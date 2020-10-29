package br.gov.sp.fatec.backend.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
  @Autowired
  private ActiveUserManager activeUserManager;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
    
    String newUser = header.getUser().getName();
    String sessionId = header.getMessageHeaders().get("simpSessionId").toString();

    System.out.println("Novo usuário conectado: [ " + newUser + " ]");
    
    activeUserManager.addUser(newUser, sessionId);
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());

    String username = header.getSessionAttributes().get("username").toString();

    System.out.println("Usuário desconectado: [ " + username + " ]");
    
    header.getSessionAttributes().remove("username");

    activeUserManager.removeUser(username);
  }
}