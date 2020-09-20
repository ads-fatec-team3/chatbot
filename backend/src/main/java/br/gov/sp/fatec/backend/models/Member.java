package br.gov.sp.fatec.backend.models;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMember")
    private long idMember;
    
    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "idConversation")
    private Conversation idConversation;
    
    @Column(name = "idUser")
    private long idUser;


    public long getIdMember() {
        return idMember;
    }

    public void setIdMember(long idMember) {
        this.idMember = idMember;
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
    
    

}