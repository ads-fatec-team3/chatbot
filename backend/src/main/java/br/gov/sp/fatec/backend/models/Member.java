package br.gov.sp.fatec.backend.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "member")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private long id;

  @Column(name = "member_name", nullable = false)
  private String name;

  @Column(name = "user_id", nullable = true)
  private Integer userId;

  @ManyToMany
  @JoinTable(name = "member_conversation",
             joinColumns = @JoinColumn(name = "conversation_id"),
             inverseJoinColumns = @JoinColumn(name = "member_id"))
  @JsonIdentityReference(alwaysAsId = true)
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