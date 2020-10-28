package br.gov.sp.fatec.backend.websocket;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class UserInterceptor implements ChannelInterceptor {
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (accessor.getCommand().equals(StompCommand.CONNECT)) {
      Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
      if (raw instanceof Map) {
        Object name = ((Map) raw).get("username");

        if (name instanceof LinkedList) {
          accessor.setUser(new UserSocket(((LinkedList) name).get(0).toString()));
        }
      }
    }

    return message;
  }
}