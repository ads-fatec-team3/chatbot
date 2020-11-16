package br.gov.sp.fatec.backend.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class GrulyUser extends User {
  private static final long serialVersionUID = 1L;

  private String name;

  public GrulyUser(String name, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}