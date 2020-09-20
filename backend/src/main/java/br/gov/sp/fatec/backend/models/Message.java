package br.gov.sp.fatec.backend.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMessage")
    private long idMessage;

    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "idConversation")
    private Conversa idConversation;
    
    @Column(name = "idUser")
    private long idUser;
    
    @Column(name = "dateHour")
    private Date dateHour;

	public long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(long idMessage) {
		this.idMessage = idMessage;
	}

	public Conversa getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(Conversa idConversation) {
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