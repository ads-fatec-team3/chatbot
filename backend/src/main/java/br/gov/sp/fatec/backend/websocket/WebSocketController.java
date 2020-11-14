package br.gov.sp.fatec.backend.websocket;

import java.util.Date;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
  @MessageMapping("/chat/{chatId}/messages")
  public ChatMessage sendMessage(@DestinationVariable("chatId") long chatId, @Payload ChatMessage message, SimpMessageHeaderAccessor header) throws Exception {
    message.setTimestamp(new Date(System.currentTimeMillis()));
    return message;
  }
}