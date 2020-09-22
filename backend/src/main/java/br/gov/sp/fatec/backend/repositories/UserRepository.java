package br.gov.sp.fatec.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.name = ?1")
    public User findByName(String name);
}