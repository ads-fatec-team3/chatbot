package br.gov.sp.fatec.backend.security;

import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


//classe para melhor controle do filtro do Spring-security
public class JwtAuthenticationFilter extends GenericFilterBean { // GenericFilterBean: filtro genérico do Spring

    @Override                                                              //filtro chain p/ passar a requisição adiante
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException { 
        
        try {
            HttpServletRequest servletRequest = (HttpServletRequest) request;//cast d/ requisição p/ HttpServletRequest
            String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION); //get no conteúdo authorization do header 
           
            if (authorization != null) {
            
                Authentication credentials =  (Authentication) JwtUtils.parseToken(authorization.replaceAll("Bearer ", "")); // parse pra validar o token e loagar
                SecurityContextHolder.getContext().setAuthentication(credentials);
                 
            }
            
            chain.doFilter(request, response); //vai pra rota
        }
        
        catch(Throwable t) {
            
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, t.getMessage()); //token inválido: 401
        }
    }

}