package br.gov.sp.fatec.backend.models;

public class Auth {
  private String username;
  private String password;
  private String token;
  private String role;

  public Auth() {}

  public Auth(String username, String password, String token) {
    this.username = username;
    this.password = password;
    this.token = token;
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