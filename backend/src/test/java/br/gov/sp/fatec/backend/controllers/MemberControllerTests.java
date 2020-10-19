package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.repositories.MemberRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

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
public class MemberControllerTests {
  private final String BASE_API_MEMBERS_URL = "api/members";

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void contextLoads() {}

  @Test
  public void insertMember() throws Exception {
    Member newMember = new Member("new member", 10);

    mockMvc.perform(
      post("/{API_URL}", BASE_API_MEMBERS_URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(newMember)))
      .andExpect(status().isCreated());
  }

  @Test
  public void getMember() throws Exception {
    Member member = memberRepository.save(new Member("member", 10));
    
    mockMvc.perform(
      get("/{API_URL}/{memberId}", BASE_API_MEMBERS_URL, member.getId())
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.data.name", is("member")))
      .andExpect(jsonPath("$.data.userId", is(10)));
    }
    
  @Test
  public void updateMember() throws Exception {
    Member updatedMember = memberRepository.save(new Member("member", 10));

    mockMvc.perform(
      put("/{API_URL}/{memberId}", BASE_API_MEMBERS_URL, updatedMember.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new Member("member updated", 20))))
      .andExpect(status().isOk());
    
    assertThat(updatedMember.getName()).isEqualTo("member updated");
    assertThat(updatedMember.getUserId()).isEqualTo(20);
  }

  @Test
  public void deleteMember() throws Exception {
    Member memberToDelete = memberRepository.save(new Member("member", 10));

    mockMvc.perform(
      delete("/{API_URL}/{memberId}", BASE_API_MEMBERS_URL, memberToDelete.getId()))
      .andExpect(status().isOk());
  }
}