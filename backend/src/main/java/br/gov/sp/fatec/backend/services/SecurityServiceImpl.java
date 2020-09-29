package br.gov.sp.fatec.backend.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ConversationRepository conversationRepo;

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Transactional
    public Message insertMessage(String text, Date timestamp, long conversation, long sender) {
        Conversation conv = conversationRepo.findConversationById(conversation);

        if (conv == null) {
            conv = new Conversation();

            conv.setTitle("Conversation Test");
            conversationRepo.save(conv);
        }

        Member member = memberRepo.findMemberById(sender);

        if (member == null) {
            member = new Member();

            member.setName("Member Test");
            member.setUserId(2);
            memberRepo.save(member);
        }

        Message message = new Message();

        message.setConversation(conv);
        message.setSender(member);
        message.setText(text);
        message.setTimestamp(timestamp);

        messageRepo.save(message);

        return message;
    }
    
    @Transactional
    public Member addToConversation(long id, long conversation) {
        Conversation conv = conversationRepo.findConversationById(conversation);

        if (conv == null) {
            conv = new Conversation();

            conv.setTitle("Conversation Test");
            conversationRepo.save(conv);
        }        

        Member member = memberRepo.findMemberById(id);

        if (member == null) {
            member = new Member();

            member.setName("Member Test");
            member.setUserId(2);
            memberRepo.save(member);
        }

        member.addConversation(conv);

        return member;
    }
}