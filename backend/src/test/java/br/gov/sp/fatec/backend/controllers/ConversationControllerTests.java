package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Disabled
public class ConversationControllerTests {
  private final String BASE_API_CONVERSATIONS_URL = "api/conversations";

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MemberRepository memberRepository;
  
  @Autowired
  ConversationRepository conversationRepository;

  @Autowired
  MessageRepository messageRepository;

  @Test
  public void contextLoads() {}

  @Test
  public void insertConversation() throws Exception {
    Conversation chat = new Conversation("chat");

    mockMvc.perform(
      post("/{API_URL}", BASE_API_CONVERSATIONS_URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(chat)))
      .andExpect(status().isCreated());
  }

  @Test
  public void getConversation() throws Exception {
    Conversation chat = conversationRepository.save(new Conversation("chat"));
    
    mockMvc.perform(
      get("/{API_URL}/{conversationId}", BASE_API_CONVERSATIONS_URL, chat.getId())
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.title", is("chat")))
      .andExpect(jsonPath("$.messages", empty()))
      .andExpect(jsonPath("$.members", empty()));
  }
    
  @Test
  public void updateConversation() throws Exception {
    Conversation updatedChat = conversationRepository.save(new Conversation("chat"));

    mockMvc.perform(
      put("/{API_URL}/{conversationId}", BASE_API_CONVERSATIONS_URL, updatedChat.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new Conversation("chat updated"))))
      .andExpect(status().isOk());
    
    assertThat(updatedChat.getTitle()).isEqualTo("chat updated");
    assertThat(updatedChat.getMembers()).asList().isEmpty();
    assertThat(updatedChat.getMessages()).asList().isEmpty();
  }

  @Test
  public void deleteConversation() throws Exception {
    Conversation chatToDelete = conversationRepository.save(new Conversation("chat"));

    mockMvc.perform(
      delete("/{API_URL}/{conversationId}", BASE_API_CONVERSATIONS_URL, chatToDelete.getId()))
      .andExpect(status().isOk());
  }

  @Test
  public void insertConversationMember() throws Exception {
    Member member = memberRepository.save(new Member("member"));
    Conversation chat = conversationRepository.save(new Conversation("chat"));

    mockMvc.perform(
      put("/{API_URL}/{conversationId}/members/{memberId}/add",
          BASE_API_CONVERSATIONS_URL, chat.getId(), member.getId()))
      .andExpect(status().isOk());

    assertThat(chat.getMembers()).asList().isNotEmpty();
  }
  
  @Test
  public void removeConversationMember() throws Exception {
    Member member = memberRepository.save(new Member("member"));
    Conversation chat = conversationRepository.save(new Conversation("chat"));
    
    memberRepository.save(member);
    
    chat.addMember(member);
    conversationRepository.save(chat);

    mockMvc.perform(
      delete("/{API_URL}/{conversationId}/members/{memberId}/remove",
      BASE_API_CONVERSATIONS_URL, chat.getId(), member.getId()))
      .andExpect(status().isOk());

    assertThat(chat.getMembers()).asList().isEmpty();
  }
}
