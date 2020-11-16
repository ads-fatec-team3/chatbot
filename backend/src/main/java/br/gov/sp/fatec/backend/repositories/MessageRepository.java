package br.gov.sp.fatec.backend.repositories;

import br.gov.sp.fatec.backend.models.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {
  Message findMessageById(long messageId);

  @Query("select m from Message m inner join m.sender s where s.id = ?2 and m.text = ?1")
  public Message findByTextAndSender(String text, long sender);
}