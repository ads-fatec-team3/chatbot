package br.gov.sp.fatec.backend.services;

import br.gov.sp.fatec.backend.models.Conversation;

public interface SecurityService {
    
    public Conversation insertConversation(String title, long userId, long idMessage);
}