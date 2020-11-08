package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.MemberRole;
import br.gov.sp.fatec.backend.models.RolePrivilege;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MemberRoleRepository;
import br.gov.sp.fatec.backend.repositories.RolePrivilegeRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

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
public class MemberControllerTests {
  private final String BASE_API_MEMBERS_URL = "api/members";

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  MemberRoleRepository memberRoleRepository;

  @Autowired
  RolePrivilegeRepository rolePrivilegeRepository;

  @Test
  public void contextLoads() {}

  @Test
  public void insertMember() throws Exception {
    Member newMember = new Member("new member");

    mockMvc.perform(
      post("/{API_URL}", BASE_API_MEMBERS_URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(newMember)))
      .andExpect(status().isCreated());
  }

  @Test
  public void getMember() throws Exception {
    Member member = memberRepository.save(new Member("member"));
    
    mockMvc.perform(
      get("/{API_URL}/{memberId}", BASE_API_MEMBERS_URL, member.getId())
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name", is("member")));
    }
    
  @Test
  public void updateMember() throws Exception {
    Member updatedMember = memberRepository.save(new Member("member"));

    mockMvc.perform(
      put("/{API_URL}/{memberId}", BASE_API_MEMBERS_URL, updatedMember.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new Member("member updated"))))
      .andExpect(status().isOk());
    
    assertThat(updatedMember.getName()).isEqualTo("member updated");
  }

  @Test
  public void deleteMember() throws Exception {
    Member memberToDelete = memberRepository.save(new Member("member"));

    mockMvc.perform(
      delete("/{API_URL}/{memberId}", BASE_API_MEMBERS_URL, memberToDelete.getId()))
      .andExpect(status().isOk());
  }

  @Test
  public void updateMemberRole() throws Exception {
    RolePrivilege readPrivilege = new RolePrivilege("READ_PRIVILEGE");
    RolePrivilege writePrivilege = new RolePrivilege("WRITE_PRIVILEGE");

    rolePrivilegeRepository.save(readPrivilege);
    rolePrivilegeRepository.save(writePrivilege);

    Set<RolePrivilege> directorPrivileges = new HashSet<RolePrivilege>();
    directorPrivileges.add(readPrivilege);
    directorPrivileges.add(writePrivilege);

    MemberRole directorRole = new MemberRole("ROLE_DIRECTOR");
    directorRole.setPrivileges(directorPrivileges);

    memberRoleRepository.save(directorRole);

    Member director = new Member("director");

    memberRepository.save(director);

    mockMvc.perform(
      put("/{API_URL}/{memberId}/role", BASE_API_MEMBERS_URL, director.getId())
      .param("roleId", String.valueOf(directorRole.getId())))
      .andExpect(status().isOk());

    assertThat(director.getRole().getName()).isEqualTo(directorRole.getName());
    assertThat(director.getRole().getPrivileges()).isNotEmpty();
    assertThat(director.getRole().getPrivileges()).isEqualTo(directorPrivileges);
  }
}
