package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.exceptions.MemberException.MemberCrudException;
import br.gov.sp.fatec.backend.exceptions.MemberException.MemberNotFoundException;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.views.Views;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@Api("API REST Gruly Member")
@CrossOrigin(origins = "*")
public class MemberController {
  @Autowired
  private MemberRepository memberRepository;

  @JsonView(Views.DetailMemberView.class)
  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todos os membros")
  public ResponseEntity<List<Member>> getAllMembers() {
    List<Member> members = memberRepository.findAll();

    return ResponseEntity.ok(members);
  }

  @JsonView(Views.DetailMemberView.class)
  @GetMapping("/{memberId}")
  @ApiOperation(value = "Retorna os dados de um membro")
  public ResponseEntity<Member> getMemberById(@PathVariable("memberId") long memberId) throws MemberNotFoundException {    
    Member fetchedMember = memberRepository.findMemberById(memberId);

    if(fetchedMember == null) {
      throw new MemberNotFoundException(memberId);
    }

    return ResponseEntity.ok(fetchedMember);
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de um membro")
  public ResponseEntity<Member> insert(@RequestBody Member member) throws MemberCrudException {
    Member newMember = memberRepository.save(member);

    if(newMember == null) {
      throw new MemberCrudException("erro ao criar um membro");
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{memberId}")
  @ApiOperation(value = "Atualiza os dados de um membro")
  public ResponseEntity<Member> update(@PathVariable("memberId") long memberId,
                                       @RequestBody Member memberDataToUpdate) throws MemberCrudException {
    Member member = memberRepository.findMemberById(memberId);

    if(member == null) {
      throw new MemberNotFoundException(memberId);
    }
    
    if(memberDataToUpdate.getName() != null) member.setName(memberDataToUpdate.getName());
    if(memberDataToUpdate.getUserId() != null) member.setUserId(memberDataToUpdate.getUserId());
    if(memberDataToUpdate.getConversations() != null) member.setConversations(memberDataToUpdate.getConversations());

    Member updatedMember = memberRepository.save(member);

    if(updatedMember == null) {
      throw new MemberCrudException(String.format("erro ao atualizar os dados do membro de id = %d", memberId));
    }

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{memberId}")
  @ApiOperation(value = "Deleta os dados de um membro")
  public ResponseEntity<Member> delete(@PathVariable("memberId") long memberId) throws MemberCrudException {
    Member memberToDelete = memberRepository.findMemberById(memberId);
    
    if(memberToDelete == null) {
      throw new MemberCrudException(String.format("erro ao deletar o membro de id = %d", memberId));
    }
    
    memberRepository.deleteById(memberId);

    return ResponseEntity.ok().build();
  }
}