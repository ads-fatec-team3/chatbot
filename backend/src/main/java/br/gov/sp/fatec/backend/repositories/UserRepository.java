package br.gov.sp.fatec.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }