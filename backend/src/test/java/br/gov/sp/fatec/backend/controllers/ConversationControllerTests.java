package br.gov.sp.fatec.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
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

  @Test
  public void contextLoads() {}

  @Test
  public void insertConversation() throws Exception {
    Conversation chat = new Conversation("chat");

    mockMvc.perform(
      post("/{API_URL}", BASE_API_CONVERSATIONS_URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(chat))
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  public void getConversation() throws Exception {
    Conversation savedChat = conversationRepository.save(new Conversation("chat"));
    
    mockMvc.perform(
      get("/{API_URL}/{conversationId}", BASE_API_CONVERSATIONS_URL, savedChat.getId())
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.data.title", is("chat")))
      .andExpect(jsonPath("$.data.messages", empty()))
      .andExpect(jsonPath("$.data.members", empty()));
    }
    
    @Test
    public void updateConversation() throws Exception {
      Conversation chatToUpdate = conversationRepository.save(new Conversation("chat"));
      chatToUpdate.setTitle("chat updated");

      mockMvc.perform(
        put("/{API_URL}/{conversationId}", BASE_API_CONVERSATIONS_URL, chatToUpdate.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(chatToUpdate)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title", is("chat updated")));
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
    long memberId = memberRepository.save(new Member("new member", 10)).getId();
    long chatId = conversationRepository.save(new Conversation("chat")).getId();

    mockMvc.perform(
      put("/{API_URL}/{conversationId}/members/{memberId}/add",
          BASE_API_CONVERSATIONS_URL, chatId, memberId))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.members", hasSize(1)));
  }
  
  @Test
  public void deleteConversationMember() throws Exception {
    Member member = memberRepository.save(new Member("new member", 10));
    Conversation chat = conversationRepository.save(new Conversation("chat"));

    memberRepository.save(member);

    chat.addMember(member);
    conversationRepository.save(chat);

    mockMvc.perform(
      delete("/{API_URL}/{conversationId}/members/{memberId}/delete",
             BASE_API_CONVERSATIONS_URL, chat.getId(), member.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.members", empty()));
  }
}