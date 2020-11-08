package br.gov.sp.fatec.backend.websocket;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
  @MessageMapping("/chat.messages")
  public ChatMessage sendMessage(@Payload ChatMessage message, SimpMessageHeaderAccessor header) throws Exception {
    message.setTimestamp(new Date(System.currentTimeMillis()));
    return message;
  }
}