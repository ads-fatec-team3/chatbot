package br.gov.sp.fatec.backend.services;

import br.gov.sp.fatec.backend.exceptions.ConversationException.ConversationNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MemberException.MemberNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MessageException.MessageCrudException;
import br.gov.sp.fatec.backend.exceptions.MessageException.MessageNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;

@Service("MessageService")
@Transactional
public class MessageServiceImpl implements MessageService {
  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ConversationRepository conversationRepository;

  @Override
  @PreAuthorize("isAuthenticated()")
  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Message getMessageById(long messageId) throws MessageNotFoundException {
    Message fetchedMessage = messageRepository.findMessageById(messageId);

    if(fetchedMessage == null) {
      throw new MessageNotFoundException(messageId);
    }

    return fetchedMessage;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Message createMessage(Message message, long senderId, long conversationId) throws MemberNotFoundException,
                                                                                           ConversationNotFoundException,
                                                                                           MessageCrudException {
    Member sender = memberRepository.findMemberById(senderId);
    Conversation chat = conversationRepository.findConversationById(conversationId);

    if (chat == null) {
      throw new ConversationNotFoundException(conversationId);
    }

    if (sender == null) {
      throw new MemberNotFoundException(senderId);
    }

    message.setSender(sender);
    message.setConversation(chat);

    Message newMessage = messageRepository.save(message);

    if (newMessage == null) {
      throw new MessageCrudException("erro ao criar uma mensagem");
    }

    return newMessage;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Message updateMessageById(long messageId, Message messageDataToUpdate) throws MessageNotFoundException, MessageCrudException {
    Message message = messageRepository.findMessageById(messageId);

    if(message == null) {
      throw new MessageNotFoundException(messageId);
    }

    if(messageDataToUpdate.getText() != null) message.setText(messageDataToUpdate.getText());

    Message updatedMessage = messageRepository.save(message);

    if(updatedMessage == null) {
      throw new MessageCrudException(String.format("erro ao atualizar os dados da mensagem de id = %d", messageId));
    }

    return updatedMessage;
  }

  @Override
  @PreAuthorize("isAuthenticated() and hasRole('ROLE_DIRECTOR')")
  public void deleteMessageById(long messageId) {
    Message messageToDelete = messageRepository.findMessageById(messageId);

    if(messageToDelete == null) {
      throw new MessageNotFoundException(messageId);
    }

    messageRepository.deleteById(messageId);
  }
}