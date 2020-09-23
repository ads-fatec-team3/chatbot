package br.gov.sp.fatec.backend.repositories;

import br.gov.sp.fatec.backend.models.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findMessageById(long id);
}