package br.gov.sp.fatec.backend.websocket;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  @Autowired
  private SimpUserRegistry userRegistry;

  @MessageMapping("/chat.messages")
  public ChatMessage sendMessage(@Payload ChatMessage message, SimpMessageHeaderAccessor header) throws Exception {
    message.setTimestamp(new Date(System.currentTimeMillis()));
    return message;
  }
}