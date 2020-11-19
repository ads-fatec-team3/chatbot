package br.gov.sp.fatec.backend.websocket;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEventListener {
  @Autowired
  private SimpUserRegistry userRegistry;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @EventListener
  public void handleSessionConnectedListener(SessionConnectedEvent event) {
    StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
    
    String newUser = header.getUser().getName();
    
    System.out.println("Novo usuário conectado: [ " + newUser + " ]");
  }

  @EventListener
  public void handleSessionDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());

    String token = header.getSessionAttributes().get("token").toString();

    System.out.println("Usuário desconectado: [ " + token + " ]");

    List<String> activeUsers = userRegistry.getUsers().stream()
                                           .map(user -> user.getName())
                                           .filter(user -> user != token)
                                           .collect(Collectors.toList());
    messagingTemplate.convertAndSend("/topic/active", activeUsers);
    
    header.getSessionAttributes().remove("token");
  }

  @EventListener
  public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
    StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
    String destination = header.getMessageHeaders().get("simpDestination").toString();

    if(destination.equals("/topic/active")) {
      List<String> activeUsers = userRegistry.getUsers().stream()
                                             .map(user -> user.getName())
                                             .collect(Collectors.toList());
      messagingTemplate.convertAndSend("/topic/active", activeUsers);
    }
  }
}