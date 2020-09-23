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
    
    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna os dados de um membro com um id específico")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
            memberRepository.findMemberById(id)
        );
    }

    @PostMapping
    @ApiOperation(value = "Insere os dados de um membro")
    public ResponseEntity<Member> insert(@RequestBody Member member) {
        return ResponseEntity.ok(
            memberRepository.save(member)
        );
    }
    
    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza os dados de um membro")
    public ResponseEntity<Member> update(@PathVariable("id") long id,
                                         @RequestBody Member newMember) {
        Member member = memberRepository.findMemberById(id);

        if(newMember.getName() != null) member.setName(newMember.getName());
        if(newMember.getUserId() != null) member.setUserId(newMember.getUserId());

        return ResponseEntity.ok(
            memberRepository.save(member)
        );
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta os dados de um membro")
    public void delete(@PathVariable("id") long id) {
        memberRepository.deleteById(id);
    }
}