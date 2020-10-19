package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.utils.GrulyApiExceptionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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

  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todos os membros")
  public List<Member> getAllMembers() {
    return memberRepository.findAll();
  }

  @GetMapping("/{memberId}")
  @ApiOperation(value = "Retorna os dados de um membro")
  public ResponseEntity<GrulyApiExceptionResponse<Member>> getMemberById(@PathVariable("memberId") long memberId) {
    GrulyApiExceptionResponse<Member> response = new GrulyApiExceptionResponse<Member>();
    
    Member fetchedMember = memberRepository.findMemberById(memberId);

    if(fetchedMember == null) {
      response.addErrorMessage("membro não encontrado");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    response.setData(fetchedMember);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de um membro")
  public ResponseEntity<GrulyApiExceptionResponse<Member>> insert(@RequestBody Member member) {
    GrulyApiExceptionResponse<Member> response = new GrulyApiExceptionResponse<Member>();

    Member newMember = memberRepository.save(member);

    if(newMember == null) {
      response.addErrorMessage("erro ao criar o membro");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{memberId}")
  @ApiOperation(value = "Atualiza os dados de um membro")
  public ResponseEntity<GrulyApiExceptionResponse<Member>> update(@PathVariable("memberId") long memberId,
                                                                  @RequestBody Member memberDataToUpdate) {
    GrulyApiExceptionResponse<Member> response = new GrulyApiExceptionResponse<Member>();

    Member member = memberRepository.findMemberById(memberId);

    if(member == null) {
      response.addErrorMessage("membro não encontrado");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    if(memberDataToUpdate.getName() != null) member.setName(memberDataToUpdate.getName());
    if(memberDataToUpdate.getUserId() != null) member.setUserId(memberDataToUpdate.getUserId());
    if(memberDataToUpdate.getConversations() != null) member.setConversations(memberDataToUpdate.getConversations());

    Member updatedMember = memberRepository.save(member);

    if(updatedMember == null) {
      response.addErrorMessage("erro ao atualizar os dados do membro");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{memberId}")
  @ApiOperation(value = "Deleta os dados de um membro")
  public ResponseEntity<GrulyApiExceptionResponse<Member>> delete(@PathVariable("memberId") long memberId) {
    GrulyApiExceptionResponse<Member> response = new GrulyApiExceptionResponse<Member>();

    Member memberToDelete = memberRepository.findMemberById(memberId);
    
    if(memberToDelete == null) {
      response.addErrorMessage("membro não encontrado");
      
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    memberRepository.deleteById(memberId);

    return ResponseEntity.ok().build();
  }
}