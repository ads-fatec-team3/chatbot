package br.gov.sp.fatec.backend.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired 
    private PasswordEncoder passEncoder;

    @Transactional
    public Message insertMessage(String text, Date timestamp, long conversationId, long senderId) {
        Conversation conversation = conversationRepository.findConversationById(conversationId);

        if (conversation == null) {
            conversation = new Conversation();
            conversation.setTitle("Conversation Test");

            conversationRepository.save(conversation);
        }

        Member sender = memberRepository.findMemberById(senderId);

        if (sender == null) {
            sender = new Member();
            sender.setName("Member Test");
            sender.setUserId(2);
    
            memberRepository.save(sender);
        }

        Message message = new Message();

        message.setConversation(conversation);
        message.setSender(sender);
        message.setText(text);
        message.setTimestamp(timestamp);

        messageRepository.save(message);

        return message;
    }
    
    @Transactional
    public Member addToConversation(long memberId, long conversationId) {
        Conversation conversation = conversationRepository.findConversationById(conversationId);

        if (conversation == null) {
            conversation = new Conversation();
            conversation.setTitle("Conversation Test");

            conversationRepository.save(conversation);
        }        

        Member member = memberRepository.findMemberById(memberId);

        if (member == null) {
            member = new Member();
            member.setUserId(2);
            member.setName("Member Test");
            
            memberRepository.save(member);
        }

        conversation.addMember(member);

        return member;
    }
}