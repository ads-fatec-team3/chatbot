package br.gov.sp.fatec.backend.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;


@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idConversation")
    private long idConversation;

    @Column(name = "title")
    private String title;
    
    @OneToMany
    @JoinTable(name = "member_conversation",
            joinColumns = {@JoinColumn(name = "idConversation")},
            inverseJoinColumns = {@JoinColumn(name = "idMember")}
    )   
    private List<Member> members;
    
    @OneToMany
    @JoinTable (name = "message_conversation",
        joinColumns = { @JoinColumn (name = "idConversation")},
        inverseJoinColumns = { @JoinColumn (name = "idMessage")}
    )
    private List<Message> messages;

    public long getIdConversation() {
        return idConversation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
    
}