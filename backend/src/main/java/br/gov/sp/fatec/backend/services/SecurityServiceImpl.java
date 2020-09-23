package br.gov.sp.fatec.backend.services;

import java.util.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.models.User;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
import br.gov.sp.fatec.backend.repositories.UserRepository;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private ConversationRepository conversationRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Transactional
    public Conversation insertConversation(String title, long userId, long idMessage) {
        Message messages = messageRepo.findByIdMessage(idMessage);

        if (messages == null) {
            messages = new Message();

            User user = new User();
            user.setName("Usuário XPTO");
            userRepo.save(user);

            messages.setIdUser(user.getId());
            messages.setDateHour(new Date(2020, 10, 2));
            messageRepo.save(messages);
        }

        Member member = memberRepo.findMemberByUser(userId);

        Conversation conversation = new Conversation();

        conversation.setTitle(title);
        conversation.setMembers(new ArrayList<Member>());
        conversation.getMembers().add(member);
        conversation.setMessages(new ArrayList<Message>());
        conversation.getMessages().add(messages);

        conversationRepo.save(conversation);

        return conversation;
    }
    
}