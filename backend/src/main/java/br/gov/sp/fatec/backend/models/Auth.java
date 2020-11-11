package br.gov.sp.fatec.backend.models;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.backend.views.Views;

public class Auth {
  @JsonView({Views.SummaryAuthView.class})
  private long id;

  private String username;

  private String password;

  @JsonView({Views.SummaryAuthView.class})
  private String token;

  @JsonView({Views.SummaryAuthView.class})
  private String role;

  public Auth() {}

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public String getToken() {
    return token;
  }

  public String getRole() {
    return role;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setRole(String role) {
    this.role = role;
  }
}