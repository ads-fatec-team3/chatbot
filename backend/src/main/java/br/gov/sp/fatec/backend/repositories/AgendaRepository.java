package br.gov.sp.fatec.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.backend.models.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Agenda findAgendById(long id);

    @Query("select a from Agenda a where a.title = ?1")
    public Agenda findByTitle(String title);

    @Query("select a from Agenda a where a.description = ?1")
    public Agenda findByDescription(String description);

    @Query("select a from Agenda a where a.status = ?1")
    public Agenda findByStatus(String status);

    @Query("select a from Agenda a inner join a.members m where m.id = ?1")
    public Agenda findByMember(long id);

}