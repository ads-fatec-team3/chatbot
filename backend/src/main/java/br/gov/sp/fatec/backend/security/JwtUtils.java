package br.gov.sp.fatec.backend.security;

import br.gov.sp.fatec.backend.models.Auth;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

  public static String generateToken(Authentication user, long userId) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);

    Auth userWithoutPassword = new Auth();
    userWithoutPassword.setId(userId);
    userWithoutPassword.setUsername(user.getName());

    if (!user.getAuthorities().isEmpty()) {
      userWithoutPassword.setRole(user.getAuthorities().iterator().next().getAuthority());
    }
    
    // gerando uma string a partir do json
    String userJson = objectMapper.writeValueAsString(userWithoutPassword);

    Date now = new Date();
    Long hour = 1000L * 60L * 60L;

    return Jwts.builder().claim("userDetails", userJson)
                         .setIssuer("br.gov.sp.fatec").setSubject(user.getName())
                         // definir tempo de expiração e assinatura com a chave
                         .setExpiration(new Date(now.getTime() + hour)).signWith(SignatureAlgorithm.HS512, KEY)
                         .compact(); // token tipo string compactada
  }

  // método para validar o token gerado pelo método acima
  public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);

    String credentialsJson = Jwts.parser() // validação de todas as claims registradas
                                 .setSigningKey(KEY) // setar a chave
                                 .parseClaimsJws(token) // faz parse do token
                                 .getBody() // pegar o corpo do token
                                 .get("userDetails", String.class); // pega o campo userDetails

    // transformando o valor da string novamente em objeto do tipo Auth
    Auth auth = objectMapper.readValue(credentialsJson, Auth.class);

    UserDetails user = User.builder()
               .username(auth.getUsername())
               .password("")
               .authorities(auth.getRole())
               .build();

    return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
  }
}