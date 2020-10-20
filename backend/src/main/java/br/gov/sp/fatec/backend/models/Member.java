package br.gov.sp.fatec.backend.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.backend.views.Views;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "member")
public class Member {
  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private long id;

  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "member_name", nullable = false)
  private String name;

  @Column(name = "user_id", nullable = true)
  private Integer userId;

  @JsonView(Views.DetailMemberView.class)
  @ManyToMany
  @JoinTable(name = "member_conversation",
             joinColumns = @JoinColumn(name = "conversation_id"),
             inverseJoinColumns = @JoinColumn(name = "member_id"))
  private Set<Conversation> conversations = new HashSet<Conversation>();

  public Member() {}

  public Member(String name, Integer userId) {
    this.name = name;
    this.userId = userId;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getUserId() {
    return userId;
  }

  public Set<Conversation> getConversations() {
    return conversations;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUserId(Integer id) {
    this.userId = id;
  }

  public void setConversations(Set<Conversation> conversations) {
    this.conversations = conversations;
  }
}