package br.gov.sp.fatec.backend.websocket;

import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController implements ActiveUserChangeListener {
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

  @SubscribeMapping("/active")
  public void getActiveUsers(SimpMessageHeaderAccessor header) {
    messagingTemplate.convertAndSend("/topic/active", activeUserManager.getActiveUsers());
  }

  @MessageMapping("/chat")
  public void sendMessage(SimpMessageHeaderAccessor header,
  //@DestinationVariable long chatId,
  @Payload ChatMessage message) throws Exception {
    message.setTimestamp(new Date(System.currentTimeMillis()));
  
    messagingTemplate.convertAndSend("/topic/broadcast", message);
    //messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", message);
  }

  @Override
  public void notifyActiveUserChange() {
    Set<String> activeUsers = activeUserManager.getActiveUsers();
    messagingTemplate.convertAndSend("/topic/active", activeUsers);
  }
}