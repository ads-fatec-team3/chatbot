package br.gov.sp.fatec.backend;

import java.util.Date;

import javax.transaction.Transactional;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.models.User;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
import br.gov.sp.fatec.backend.repositories.UserRepository;
import br.gov.sp.fatec.backend.services.SecurityService;

@SpringBootTest
// @Transactional
// @Rollback
@TestMethodOrder(OrderAnnotation.class)
class BackendApplicationTests {

    @Autowired
    private ConversationRepository conversationRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private SecurityService securityService;

	@Test
	void contextLoads() {
    }
    
    @Order(1)
    @Test
    void insertUser() {
        User user = new User();

        user.setName("User 1");

        userRepo.save(user);
    }

    @Order(2)
    @Test
    void insertConversation() {
        User user = new User();
        user.setName("User 2");

        userRepo.save(user);

        Conversation conversation = new Conversation();

        conversation.setTitle("Conversation 1");
        conversation.setMembers(new ArrayList<Member>());

        Member member = new Member();
        member.setIdUser(user.getId());

        memberRepo.save(member);

        conversation.getMembers().add(member);
        conversation.setMessages(new ArrayList<Message>());

        Message message = new Message();
        message.setIdUser(user.getId());
        message.setDateHour(new Date(2020, 9, 22));
        
        messageRepo.save(message);

        conversation.getMessages().add(message);

        conversationRepo.save(conversation);
        assertNotNull(conversation.getMembers().iterator().next().getIdMember());
    }

    @Order(3)
    @Test
    void searchUserByName() {
        User user = userRepo.findByName("User 1");
        assertNotNull(user);
    }

    @Order(4)
    @Test
    void searchMessageByConversation() {
       Message messages = messageRepo.findByConversation("Conversation 1");
        assertNotNull(messages);
    }

    @Order(5)
    @Test
    void searchByDateAndUser() {
        Date date = new Date(2020, 9, 22);

        Message messages = messageRepo.findByDateAndUser(date, 2);
        assertNotNull(messages);
    }

    @Order(6)
    @Test
    void serviceInsertConversation() {
        Conversation conversation = securityService.insertConversation("Conversation XPTO", 1, 1L);
        assertNotNull(conversation);
    }
}
