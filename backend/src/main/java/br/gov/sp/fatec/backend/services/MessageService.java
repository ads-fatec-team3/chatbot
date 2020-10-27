package br.gov.sp.fatec.backend.services;

import br.gov.sp.fatec.backend.models.Message;

import java.util.List;

public interface MessageService {
  public List<Message> getAllMessages();

  public Message getMessageById(long messageId);

  public Message createMessage(Message message, long senderId, long conversationId);

  public Message updateMessageById(long messageId, Message messageDataToUpdate);

  public void deleteMessageById(long messageId);
}