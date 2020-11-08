package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;

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
public class MessageControllerTests {
  private final String BASE_API_MESSAGES_URL = "api/messages";

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MessageRepository messageRepository;

  @Autowired
  MemberRepository memberRepository;
  
  @Autowired
  ConversationRepository conversationRepository;

  @Test
  public void contextLoads() {}

  @Test
  public void insertMessage() throws Exception {
    Member sender = new Member("sender");
    Conversation chat = new Conversation("chat");

    memberRepository.save(sender);
    conversationRepository.save(chat);

    Message newMessage = new Message("test");

    mockMvc.perform(
      post("/{API_URL}", BASE_API_MESSAGES_URL)
      .contentType(MediaType.APPLICATION_JSON)
      .param("senderId", String.valueOf(sender.getId()))
      .param("conversationId", String.valueOf(chat.getId()))
      .content(objectMapper.writeValueAsString(newMessage)))
      .andExpect(status().isCreated());
  }

  @Test
  public void getMessage() throws Exception {
    Message savedMessage = messageRepository.save(new Message("test"));
    
    mockMvc.perform(
      get("/{API_URL}/{messageId}", BASE_API_MESSAGES_URL, savedMessage.getId())
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.text", is("test")));
    }
    
  @Test
  public void updateMessage() throws Exception {
    Message updatedMessage = messageRepository.save(new Message("test"));

    mockMvc.perform(
      put("/{API_URL}/{messageId}", BASE_API_MESSAGES_URL, updatedMessage.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new Message("test updated"))))
      .andExpect(status().isOk());

    assertThat(updatedMessage.getText()).isEqualTo("test updated");
  }

  @Test
  public void deleteMessage() throws Exception {
    Message messageToDelete = messageRepository.save(new Message("test"));

    mockMvc.perform(
      delete("/{API_URL}/{memberId}", BASE_API_MESSAGES_URL, messageToDelete.getId()))
      .andExpect(status().isOk());
  }
}