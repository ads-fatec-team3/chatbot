package br.gov.sp.fatec.backend.models;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMember")
    private long idMember;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name="member_conversation", 
        joinColumns={@JoinColumn(name="idMember")},
        inverseJoinColumns={@JoinColumn(name="idConversation")}
    )
    private Conversation conversation;
    
    @Column(name = "idUser")
    private long idUser;


    public long getIdMember() {
        return idMember;
    }

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
    
    

}