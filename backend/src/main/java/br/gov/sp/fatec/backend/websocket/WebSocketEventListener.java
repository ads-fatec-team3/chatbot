package br.gov.sp.fatec.backend.websocket;

import br.gov.sp.fatec.backend.models.MessageType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  @Autowired
  private ActiveUserManager activeUserManager;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    System.out.println("Nova conexão recebida: [" + event.getUser().getName() + "]");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());

    String username = (String) header.getSessionAttributes().get("username");

    if(username != null) {
      ChatMessage message = new ChatMessage();
      message.setMessageType(MessageType.LEAVE);
      message.setSender(username);

      messagingTemplate.convertAndSend("/topic/broadcast", message);
    }
  }
}