package br.gov.sp.fatec.backend.repositories;

import br.gov.sp.fatec.backend.models.MemberRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
  MemberRole findMemberRoleById(long id);

  @Query("SELECT mr FROM MemberRole mr WHERE mr.name = ?1")
  public MemberRole findMemberRoleByName(String name);
}