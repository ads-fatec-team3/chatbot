package br.gov.sp.fatec.backend.security;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {// calsse c/ métodos para lidar com jwt
    
    private static final String KEY = "spring.jwt.sec"; //chave de validação do token
    
    public static String generateToken(Authentication user) throws JsonProcessingException {
         
        ObjectMapper mapper = new ObjectMapper();
         Login userWithoutPassword = new Login();
         userWithoutPassword.setUserName(user.getName());
         
         if (!user.getAuthorities().isEmpty()) {

            userWithoutPassword.setAuthorization(user.getAuthorities().iterator().next().getAuthority());
         }

        String userJson = mapper.writeValueAsString(userWithoutPassword);  //gerando um json para userWithoutPassword
         
        Date now = new Date();
        Long hour = 1000L * 60L * 60L; 
        return Jwts.builder().claim("userDetails", userJson)
            .setIssuer("br.gov.sp.fatec")
            .setSubject(user.getName())
            .setExpiration(new Date(now.getTime() + hour))
            .signWith(SignatureAlgorithm.HS512, KEY) //tipo da criptografia + chave
            .compact(); // token tipo string compactada 
    }
    

    //método p/ abrir o token gerado pelo método acima
    public static User parseToken( String token) throws JsonParseException, JsonMappingException, IOException {
         
        ObjectMapper mapper = new ObjectMapper();
         
        String credentialsJson = Jwts.parser() // validação de todas as claims registradas
                .setSigningKey(KEY) //setar a chave
                .parseClaimsJws(token) //abrir o token e
                .getBody() //pegar o corpo do token
                .get("userNameDetails", String.class);
                
                Login user =  mapper.readValue(credentialsJson, Login.class); //transformando novamente na classe login

                return (User) User.builder()
                .username(user.getUserName())
                .password("admin")
                .authorities(user.getAuthorization())
                .build();

    }
}