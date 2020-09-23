package br.gov.sp.fatec.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.backend.models.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Conversation findConversationById(long id);
}