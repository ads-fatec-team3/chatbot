package br.gov.sp.fatec.backend.models;

import br.gov.sp.fatec.backend.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gruly_role_privileges")
public class RolePrivilege {
  @JsonView({Views.SummaryRolePrivilegeView.class, Views.SummaryMemberRoleView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_privilege_id")
  private long id;

  @JsonView({Views.SummaryRolePrivilegeView.class, Views.SummaryMemberRoleView.class})
  @Column(name = "role_privilege_name")
  private String name;

  @JsonView({Views.DetailRolePrivilegeView.class})
  @ManyToMany(mappedBy = "privileges")
  private Set<MemberRole> roles = new HashSet<MemberRole>();

  public RolePrivilege() {}

  public RolePrivilege(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<MemberRole> getRoles() {
    return roles;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addRole(MemberRole role) {
    this.roles.add(role);
    role.getPrivileges().add(this);
  }

  public void removeRole(MemberRole role) {
    this.roles.remove(role);
    role.getPrivileges().remove(this);
  }
}