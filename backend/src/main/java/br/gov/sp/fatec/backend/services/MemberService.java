package br.gov.sp.fatec.backend.services;

import java.util.List;

import br.gov.sp.fatec.backend.models.Member;

public interface MemberService {
  public List<Member> getAllMembers();

  public Member getMemberById(long memberId);

  public Member createMember(Member member);

  public Member updateMemberById(long memberId, Member memberDataToUpdate);

  public void deleteMemberById(long memberId);

  public void updateMemberRole(long memberId, long memberRoleId);
}