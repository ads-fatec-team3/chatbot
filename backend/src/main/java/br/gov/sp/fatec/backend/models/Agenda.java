package br.gov.sp.fatec.backend.models;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.backend.views.Views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "agenda")
public class Agenda {
  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "agenda_id")
  private long id;

  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @Column(name = "agenda_title")
  private String title;

  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @Column(name = "agenda_description")
  private String description;

  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @Column(name = "agenda_date_begin")
  private Date date_begin;

  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @Column(name = "agenda_date_end")
  private Date date_end;

  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member owner;

  @JsonView(Views.DetailConversationView.class)
  @ManyToMany(mappedBy = "agenda")
  private List<Member> members = new ArrayList<Member>();

  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @Column(name = "agenda_color")
  private String color;

  @JsonView({ Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class })
  @Column(name = "agenda_status")
  private String status;

  public Agenda() {
  }

  public Agenda(String title, String description, Date date_begin, Date date_end, String color, String status) {
    this.title = title;
    this.description = description;
    this.date_begin = date_begin;
    this.date_end = date_end;
    this.color = color;
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Date getDateBegin() {
    return date_begin;
  }

  public Date getDateEnd() {
    return date_end;
  }

  public Member getOwner() {
    return owner;
  }

  public String getColor() {
    return color;
  }

  public String getStatus() {
    return status;
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

  public void setColor(String color) {
    this.color = color;
  }

  public void setDate_begin(Date date_begin) {
    this.date_begin = date_begin;
  }

  public void setDate_end(Date date_end) {
    this.date_end = date_end;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setOwner(Member owner) {
    this.owner = owner;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
  }

  public void addMember(Member member) {
    members.add(member);
    member.getAgenda().add(this);
  }

  public void removeMember(Member member) {
    members.add(member);
    member.getAgenda().remove(this);
  }
}