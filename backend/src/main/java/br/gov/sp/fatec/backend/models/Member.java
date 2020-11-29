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
@Table(name = "gruly_members")
public class Member {
  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class,
             Views.SummaryMemberRoleView.class, Views.SummaryAgendaView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private long id;

  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class,
              Views.SummaryMemberRoleView.class, Views.SummaryAgendaView.class})
  @Column(name = "member_name", nullable = false)
  private String name;

  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class,
             Views.SummaryMemberRoleView.class})
  @Column(name = "member_username", nullable = false)
  private String username;

  @JsonView(Views.DetailMemberView.class)
  @Column(name = "member_password", nullable = true)
  private String password;

  @JsonView(Views.DetailMemberView.class)
  @ManyToMany
  @JoinTable(name = "gruly_member_conversations",
             joinColumns = @JoinColumn(name = "member_id"),
             inverseJoinColumns = @JoinColumn(name = "conversation_id"))
  private Set<Conversation> conversations = new HashSet<Conversation>();

  @JsonView(Views.DetailMemberView.class)
  @ManyToOne
  @JoinTable(name = "gruly_member_role",
             joinColumns = @JoinColumn(name = "member_id"),
             inverseJoinColumns = @JoinColumn(name = "member_role_id"))
  private MemberRole role;

  @JsonView(Views.DetailMemberView.class)
  @ManyToMany
  @JoinTable(name = "gruly_member_agenda",
             joinColumns = @JoinColumn(name = "member_id"),
             inverseJoinColumns = @JoinColumn(name = "agenda_id"))
  private Set<Agenda> agendas = new HashSet<Agenda>();

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

  public String getUsername() {
    return username;
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

  public Set<Agenda> getAgendas() {
    return agendas;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public void setAgenda(Set<Agenda> agendas) {
    this.agendas = agendas;
  }

  public void removeConversation(Conversation conversation) {
    conversations.remove(conversation);
    conversation.removeMember(this);
  }
}