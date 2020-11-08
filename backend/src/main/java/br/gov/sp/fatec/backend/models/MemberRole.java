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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gruly_member_roles")
public class MemberRole {
  @JsonView({Views.SummaryMemberRoleView.class, Views.SummaryMemberView.class, Views.SummaryRolePrivilegeView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_role_id")
  private long id;

  @JsonView({Views.SummaryMemberRoleView.class, Views.SummaryMemberView.class, Views.SummaryRolePrivilegeView.class})
  @Column(name = "member_role_name")
  private String name;

  @OneToMany(mappedBy = "role")
  private Set<Member> members = new HashSet<Member>();

  @JsonView({Views.DetailMemberRoleView.class})
  @ManyToMany
  @JoinTable(name = "gruly_member_role_privileges",
             joinColumns = @JoinColumn(name = "member_role_id"),
             inverseJoinColumns = @JoinColumn(name = "role_privilege_id"))
  private Set<RolePrivilege> privileges = new HashSet<RolePrivilege>();

  public MemberRole() {}

  public MemberRole(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<Member> getMembers() {
    return members;
  }

  public Set<RolePrivilege> getPrivileges() {
    return privileges;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMembers(Set<Member> members) {
    this.members = members;
  }

  public void setPrivileges(Set<RolePrivilege> privileges) {
    this.privileges = privileges;
  }

  public void addPrivilege(RolePrivilege privilege) {
    this.privileges.add(privilege);
    privilege.getRoles().add(this);
  }

  public void removePrivilege(RolePrivilege privilege) {
    this.privileges.remove(privilege);
    privilege.getRoles().remove(this);
  }
}