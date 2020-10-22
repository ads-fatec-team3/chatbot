package br.gov.sp.fatec.backend.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public ChatMessage sendMessage(@Payload ChatMessage msg) throws Exception {
    return msg;
  }
}