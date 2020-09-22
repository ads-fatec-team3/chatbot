package br.gov.sp.fatec.backend.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMessage")
    private long idMessage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name="message_conversation", 
        joinColumns={@JoinColumn(name="idMessage")},
        inverseJoinColumns={@JoinColumn(name="idConversation")}
    )
    private Conversation idConversation;
    
    @Column(name = "idUser")
    private long idUser;
    
    @Column(name = "dateHour")
    private Date dateHour;

	public long getIdMessage() {
		return idMessage;
	}

	public Conversation getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(Conversation idConversation) {
		this.idConversation = idConversation;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public Date getDateHour() {
		return dateHour;
	}

	public void setDateHour(Date dateHour) {
		this.dateHour = dateHour;
	}
}