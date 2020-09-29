package br.gov.sp.fatec.backend;

import java.util.Date;

import javax.transaction.Transactional;

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
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
import br.gov.sp.fatec.backend.services.SecurityService;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(OrderAnnotation.class)
class BackendApplicationTests {

    @Autowired
    private ConversationRepository conversationRepo;

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
    void insertConversation() {
        Conversation conversation = new Conversation();

        conversation.setTitle("Conversation 2");
        conversationRepo.save(conversation);

        assertNotNull(conversation);
    }

    @Order(2)
    @Test
    void insertMember() {
        Member member = new Member();

        member.setName("Member 1");
        member.setUserId(1);

        memberRepo.save(member);

        assertNotNull(member);
    }

    @Order(3)
    @Test
    void insertMessage() {
        Date date = new Date(2020, 9, 22);

        Message message = securityService.insertMessage("Test", date, 1, 2);
        assertNotNull(message);
    }

    @Order(4)
    @Test
    void addMemberToConversation() {
        Member member = securityService.addToConversation(2, 1);

        assertNotNull(member);
    }

    @Order(5)
    @Test
    void searchMessageByTextAndSender() {
        Message messages = messageRepo.findByTextAndSender("Test", 2L);
        assertNotNull(messages);
    }
}
