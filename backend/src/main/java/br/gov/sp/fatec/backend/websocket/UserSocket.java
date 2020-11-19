package br.gov.sp.fatec.backend.websocket;

import java.security.Principal;

public class UserSocket implements Principal {
  private String token;

  public UserSocket(String token) {
    this.token = token;
  }

  @Override
  public String getName() {
    return token;
  }
}