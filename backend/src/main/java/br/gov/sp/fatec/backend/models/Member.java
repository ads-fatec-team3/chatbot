package br.gov.sp.fatec.backend.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Table(name = "member")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                  property = "id")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToMany
    @JoinTable(name = "member_conversation",
               joinColumns = @JoinColumn(name = "conversation_id"),
               inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getUserId() {
        return userId;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public void addMessage(Message message) {
        messages.add(message);
        message.setSender(this);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
        message.setSender(null);
    }

    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
        conversation.getMembers().add(this);
    }
    
    public void removeConversation(Conversation conversation) {
        conversations.remove(conversation);
        conversation.getMembers().remove(this);
    }
