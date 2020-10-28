package br.gov.sp.fatec.backend.websocket;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.services.ConversationService;

import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController implements ActiveUserChangeListener {
  @Autowired
  private ConversationService conversationService;

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  @Autowired
  private ActiveUserManager activeUserManager;

  @PostConstruct
  private void init() {
    activeUserManager.subscribeListener(this);
  }

  @PreDestroy
  private void destroy() {
    activeUserManager.unsubscribeListener(this);
  }

  @SubscribeMapping("/chats/{chatId}")
  public Conversation subscribeToChatById(@DestinationVariable long chatId) {
    return conversationService.getConversationById(chatId);
  }

  @MessageMapping("/chat")
  //@SendTo("/queue/messages")
  public void sendMessage(SimpMessageHeaderAccessor header,
  //@DestinationVariable long chatId,
  @Payload ChatMessage message) throws Exception {
    String sender = header.getUser().getName();
    header.getSessionAttributes().put("username", sender);
    message.setTimestamp(new Date(System.currentTimeMillis()));
  
    messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", message);
    messagingTemplate.convertAndSend("/topic/broadcast", message);
  }

  @Override
  public void notifyActiveUserChange() {
    Set<String> activeUsers = activeUserManager.getAllUsers();
    messagingTemplate.convertAndSend("/topic/active", activeUsers);
  }
}