package br.gov.sp.fatec.backend.services;

import br.gov.sp.fatec.backend.exceptions.MemberException.MemberNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MemberException.MemberCrudException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.repositories.MemberRepository;

@Service("MemberService")
@Transactional
public class MemberServiceImpl implements MemberService {
  @Autowired
  private MemberRepository memberRepository;
  
  @Override
  public List<Member> getAllMembers() {
    return memberRepository.findAll();
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Member getMemberById(long memberId) throws MemberNotFoundException {
    Member fetchedMember = memberRepository.findMemberById(memberId);

    if(fetchedMember == null) {
      throw new MemberNotFoundException(memberId);
    }

    return fetchedMember;
  }

  @Override
  public Member createMember(Member member) throws MemberCrudException {
    Member newMember = memberRepository.save(member);

    if(newMember == null) {
      throw new MemberCrudException("erro ao criar um membro");
    }

    return newMember;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Member updateMemberById(long memberId, Member memberDataToUpdate) throws MemberNotFoundException, MemberCrudException  {
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

    return updatedMember;
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteMemberById(long memberId) throws MemberCrudException {
    Member memberToDelete = memberRepository.findMemberById(memberId);
    
    if(memberToDelete == null) {
      throw new MemberCrudException(String.format("erro ao deletar o membro de id = %d", memberId));
    }
    
    memberRepository.deleteById(memberId);
  }
}