package br.gov.sp.fatec.backend.websocket;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @Autowired
  MessageRepository messageRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  ConversationRepository conversationRepository;

  @MessageMapping("/chat/messages")
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor header) throws Exception {
    chatMessage.setTimestamp(new Date(System.currentTimeMillis()));
    
    Conversation chat = conversationRepository.findConversationById(chatMessage.getChatId());
    
    Message message = new Message();
    message.setText(chatMessage.getText());
    message.setSender(memberRepository.findMemberById(chatMessage.getSender()));
    message.setConversation(chat);
    
    messageRepository.save(message);

    chat.setLastMessage(message);
    conversationRepository.save(chat);
    
    return chatMessage;
  }
}