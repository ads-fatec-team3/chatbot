package br.gov.sp.fatec.backend.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.backend.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> { 

    @Query("select m from Message m inner join m.idConversation c where c.title = ?1")
    public Message findByConversation(String title);

    @Query("select m from Message m where m.dateHour = ?1 and m.idUser = ?2")
    public Message findByDateAndUser(Date dateHour, long idUser);
    
}