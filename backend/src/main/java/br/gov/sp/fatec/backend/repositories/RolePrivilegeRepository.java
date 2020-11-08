package br.gov.sp.fatec.backend.repositories;

import br.gov.sp.fatec.backend.models.RolePrivilege;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
  RolePrivilege findRolePrivilegeById(long id);

  @Query("SELECT rp FROM RolePrivilege rp WHERE rp.name = ?1")
  public RolePrivilege findPrivilegeByName(String name);

  @Query("SELECT rp FROM RolePrivilege rp INNER JOIN rp.roles r WHERE r.name = ?1")
  public Set<RolePrivilege> findPrivilegesByRoleName(String roleName);
}