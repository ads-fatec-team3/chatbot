package br.gov.sp.fatec.backend.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Member> members;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Message> messages;

    public long getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(long idConversation) {
        this.idConversation = idConversation;
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