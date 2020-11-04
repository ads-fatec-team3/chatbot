package br.gov.sp.fatec.backend.models;

import br.gov.sp.fatec.backend.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Table(name = "conversation")
public class Conversation {
  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "conversation_id")
  private long id;

  @JsonView({Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "conversation_title")
  private String title;

  @JsonView(Views.SummaryConversationView.class)
  @OneToMany(mappedBy = "conversation")
  private List<Message> messages = new ArrayList<Message>();

  @JsonView(Views.DetailConversationView.class)
  @ManyToMany(mappedBy = "conversations")
  private List<Member> members = new ArrayList<Member>();

  public Conversation() {}

  public Conversation(String title) {
    this.title = title;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public List<Member> getMembers() {
    return members;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public void addMessage(Message message) {
    messages.add(message);
    message.setConversation(this);
  }

  public void removeMessage(Message message) {
    messages.remove(message);
    message.setConversation(null);
  }

  public void addMember(Member member) {
    members.add(member);
    member.getConversations().add(this);
  }

  public void removeMember(Member member) {
    members.remove(member);
    member.getConversations().remove(this);
  }
}