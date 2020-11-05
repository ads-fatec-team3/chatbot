package br.gov.sp.fatec.backend.models;

import br.gov.sp.fatec.backend.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "member")
public class Member {
  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class,
             Views.SummaryMessageView.class, Views.SummaryMemberRoleView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private long id;

  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class,
             Views.SummaryMessageView.class, Views.SummaryMemberRoleView.class})
  @Column(name = "member_name", nullable = false)
  private String name;

  @JsonView(Views.DetailMemberView.class)
  @Column(name = "member_password", nullable = true)
  private String password;

  @JsonView(Views.DetailMemberView.class)
  @ManyToMany
  @JoinTable(name = "member_conversation",
             joinColumns = @JoinColumn(name = "conversation_id"),
             inverseJoinColumns = @JoinColumn(name = "member_id"))
  private Set<Conversation> conversations = new HashSet<Conversation>();

  @JsonView(Views.DetailMemberView.class)
  @ManyToOne
  @JoinTable(name = "member_role",
             joinColumns = @JoinColumn(name = "member_role_id"),
             inverseJoinColumns = @JoinColumn(name = "member_id"))
  private MemberRole role;

  public Member() {}

  public Member(String name) {
    this.name = name;
  }

  public Member(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public MemberRole getRole() {
    return role;
  }

  public Set<Conversation> getConversations() {
    return conversations;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(MemberRole role) {
    this.role = role;
  }

  public void setConversations(Set<Conversation> conversations) {
    this.conversations = conversations;
  }
}