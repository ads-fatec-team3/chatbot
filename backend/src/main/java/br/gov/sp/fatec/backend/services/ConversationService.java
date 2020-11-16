package br.gov.sp.fatec.backend.services;

import java.util.List;

import br.gov.sp.fatec.backend.models.Conversation;

public interface ConversationService {
  public List<Conversation> getAllConversations();

  public Conversation getConversationById(long conversationId);

  public List<Conversation> getConversationsByMemberId(long memberId);

  public Conversation createConversation(Conversation conversation);

  public Conversation updateConversationById(long conversationId, Conversation chatDataToUpdate);

  public void deleteConversationById(long conversationId);

  public Conversation addMemberToConversation(long memberId, long conversationId);

  public Conversation removeMemberFromConversation(long memberId, long conversationId);
}