package br.gov.sp.fatec.backend.security;

import br.gov.sp.fatec.backend.models.Auth;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtils {
  private static final String KEY = "spring.jwt.sec"; // chave de validação do token

  public static String generateToken(Authentication user) throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();
    Auth userWithoutPassword = new Auth();
    userWithoutPassword.setUsername(user.getName());

    if (!user.getAuthorities().isEmpty()) {
      userWithoutPassword.setRole(user.getAuthorities().iterator().next().getAuthority());
    }

    String userJson = objectMapper.writeValueAsString(userWithoutPassword); // gerando um json para userWithoutPassword

    Date now = new Date();
    Long hour = 1000L * 60L * 60L;
    return Jwts.builder().claim("userDetails", userJson)
                         .setIssuer("br.gov.sp.fatec").setSubject(user.getName())
                         .setExpiration(new Date(now.getTime() + hour)).signWith(SignatureAlgorithm.HS512, KEY) // tipo da criptografia + chave
                         .compact(); // token tipo string compactada
  }

  // método para validar o token gerado pelo método acima
  public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {

    ObjectMapper objectMapper = new ObjectMapper();

    String credentialsJson = Jwts.parser() // validação de todas as claims registradas
                                 .setSigningKey(KEY) // setar a chave
                                 .parseClaimsJws(token) // faz parse do token
                                 .getBody() // pegar o corpo do token
                                 .get("userDetails", String.class);

    Auth auth = objectMapper.readValue(credentialsJson, Auth.class); // transformando novamente na classe login

    UserDetails user = User.builder()
               .username(auth.getUsername())
               .password("")
               .authorities(auth.getRole())
               .build();

    return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
  }
}