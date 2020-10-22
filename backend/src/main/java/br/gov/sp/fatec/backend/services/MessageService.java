package br.gov.sp.fatec.backend.services;

import br.gov.sp.fatec.backend.models.Message;

public interface MessageService {
  public Message createMessage(Message message, long senderId, long conversationId);
}