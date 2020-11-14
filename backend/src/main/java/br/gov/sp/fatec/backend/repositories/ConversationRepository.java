package br.gov.sp.fatec.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.backend.models.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
  Conversation findConversationById(long id);

  @Query("select c from Conversation c where c.title = ?1")
  public Conversation findByTitle(String title);

  @Query("SELECT c FROM Conversation c INNER JOIN c.members m WHERE m.id = ?1")
  public List<Conversation> findConversationsByMemberId(long memberId);
}