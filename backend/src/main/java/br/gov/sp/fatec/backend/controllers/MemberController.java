package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todos os membros")
  public List<Member> getAllMembers() {
    return memberRepository.findAll();
  }

  @GetMapping("/{memberId}")
  @ApiOperation(value = "Retorna os dados de um membro")
  public ResponseEntity<Member> getMemberById(@PathVariable("memberId") long memberId) {
    Member fetchedMember = memberRepository.findMemberById(memberId);

    if(fetchedMember == null)
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(fetchedMember);
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de um membro")
  public ResponseEntity<Member> insert(@RequestBody Member member) {
    Member newMember = memberRepository.save(member);

    if(newMember == null)
      return ResponseEntity.badRequest().build();
    
    return ResponseEntity.ok(newMember);
  }

  @PutMapping("/{memberId}")
  @ApiOperation(value = "Atualiza os dados de um membro")
  public ResponseEntity<Member> update(@PathVariable("memberId") long memberId,
                                       @RequestBody Member memberDataToUpdate) {
    Member member = memberRepository.findMemberById(memberId);
    
    if(memberDataToUpdate.getName() != null) member.setName(memberDataToUpdate.getName());
    if(memberDataToUpdate.getUserId() != null) member.setUserId(memberDataToUpdate.getUserId());
    if(memberDataToUpdate.getConversations() != null) member.setConversations(memberDataToUpdate.getConversations());

    Member updatedMember = memberRepository.save(member);

    if(updatedMember == null)
      return ResponseEntity.badRequest().build();
    
    return ResponseEntity.ok(updatedMember);
  }

  @DeleteMapping("/{memberId}")
  @ApiOperation(value = "Deleta os dados de um membro")
  public ResponseEntity<Member> delete(@PathVariable("memberId") long memberId) {
    Member memberToDelete = memberRepository.findMemberById(memberId);
    
    if(memberToDelete == null)
      return ResponseEntity.notFound().build();
    else
      memberRepository.deleteById(memberId);

    return ResponseEntity.ok().build();
  }
}