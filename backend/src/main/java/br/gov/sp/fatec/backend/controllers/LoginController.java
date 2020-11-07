package br.gov.sp.fatec.backend.controllers;

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

import br.gov.sp.fatec.backend.security.Login;
import br.gov.sp.fatec.backend.security.JwtUtils;

@RestController
@RequestMapping(value = "/api/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager authManager; // objeto que vai implantar a configuração feita no SecurityConfig

    @PostMapping()
    public Login authenticator(@RequestBody Login login) throws JsonProcessingException {


        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword());
        authManager.authenticate(auth); //método para logar que recebe um objeto do tipo authentication
        login.setPassword(null); //dispensando a senha admin setada no jwtUtils
        login.setToken(JwtUtils.generateToken(auth)); //volta o token no lugar da senha
        return login;
    }
}