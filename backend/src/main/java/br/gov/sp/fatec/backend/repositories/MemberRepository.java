package br.gov.sp.fatec.backend.repositories;

import br.gov.sp.fatec.backend.models.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberById(long id);
}