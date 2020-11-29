package br.gov.sp.fatec.backend.models;

import br.gov.sp.fatec.backend.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "gruly_messages")
public class Message {
  @JsonView({Views.SummaryMessageView.class, Views.SummaryConversationView.class, Views.SummaryMemberView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "message_id")
  private long id;
  
  @JsonView({Views.SummaryMessageView.class, Views.SummaryConversationView.class, Views.SummaryMemberView.class})
  @Column(name = "message_text", nullable = false)
  private String text;
  
  @JsonView({Views.SummaryMessageView.class, Views.SummaryConversationView.class, Views.SummaryMemberView.class})
  @Column(name = "message_timestamp", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  @JsonView(Views.DetailMessageView.class)
  @ManyToOne
  @JoinColumn(name = "conversation_id")
  private Conversation conversation;

  @JsonView({Views.DetailMessageView.class, Views.DetailConversationView.class})
  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Member sender;

  public Message() {
    this.timestamp = new Date(System.currentTimeMillis());
  }
  
  public Message(String text) {
    this.text = text;
    this.timestamp = new Date(System.currentTimeMillis());
  }

  public long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public Conversation getConversation() {
    return conversation;
  }

  public Member getSender() {
    return sender;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public void setConversation(Conversation conversation) {
    this.conversation = conversation;
  }

  public void setSender(Member sender) {
    this.sender = sender;
  }
}