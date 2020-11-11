package br.gov.sp.fatec.backend.security;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.repositories.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("AuthService")
public class AuthServiceImpl implements AuthService {
  @Autowired
  private MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findMemberByUsername(username);

    if(member == null) {
      throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
    }
    
    return GrulyUser.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .authorities(new SimpleGrantedAuthority(member.getRole().getName()))
                    .build();
  }
}