package br.gov.sp.fatec.backend.models;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.backend.views.Views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "gruly_agenda")
public class Agenda {
  @JsonView({Views.SummaryAgendaView.class, Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "agenda_id")
  private long id;

  @JsonView({Views.SummaryAgendaView.class, Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "agenda_title")
  @NotNull(message = "Title is mandatory")
  private String title;

  @JsonView({Views.SummaryAgendaView.class, Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "agenda_description")
  @NotNull(message = "Description is mandatory")
  private String description;

  @JsonView({Views.SummaryAgendaView.class, Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "agenda_date_begin")
  @NotNull(message = "Date begin is mandatory")
  private Date dateBegin;

  @JsonView({Views.SummaryAgendaView.class, Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "agenda_date_end")
  @NotNull(message = "Date end is mandatory")
  private Date dateEnd;

  @JsonView(Views.DetailAgendaView.class)
  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member owner;

  @JsonView({Views.DetailAgendaView.class, Views.DetailConversationView.class})
  @ManyToMany(mappedBy = "agendas")
  private List<Member> members = new ArrayList<Member>();

  @JsonView({Views.SummaryAgendaView.class, Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "agenda_color")
  @NotNull(message = "Color is mandatory")
  private String color;

  @JsonView({Views.SummaryAgendaView.class, Views.SummaryMemberView.class, Views.SummaryConversationView.class, Views.SummaryMessageView.class})
  @Column(name = "agenda_status")
  private String status;

  public Agenda() {}

  public Agenda(String title, String description, Date dateBegin, Date dateEnd, String color, String status) {
    this.title = title;
    this.description = description;
    this.dateBegin = dateBegin;
    this.dateEnd = dateEnd;
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
    return dateBegin;
  }

  public Date getDateEnd() {
    return dateEnd;
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

  public void setDateBegin(Date dateBegin) {
    this.dateBegin = dateBegin;
  }

  public void setDateEnd(Date dateEnd) {
    this.dateEnd = dateEnd;
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
    member.getAgendas().add(this);
  }

  public void removeMember(Member member) {
    members.add(member);
    member.getAgendas().remove(this);
  }
}