package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Auth;
import br.gov.sp.fatec.backend.security.JwtUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/signin")
  public Auth signin(@RequestBody Auth auth) throws JsonProcessingException {
    UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());
    Authentication user = authenticationManager.authenticate(credentials);

    auth.setToken(JwtUtils.generateToken(user));
    auth.setPassword(credentials.getCredentials().toString());
    auth.setRole(user.getAuthorities().iterator().next().getAuthority());
  
    return auth;
  }
}