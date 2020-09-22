package br.gov.sp.fatec.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.backend.models.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { 
    
}