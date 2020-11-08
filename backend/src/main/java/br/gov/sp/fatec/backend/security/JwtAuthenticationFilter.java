package br.gov.sp.fatec.backend.security;

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
import org.springframework.web.filter.GenericFilterBean;

// classe para melhor controle do filtro do Spring Security
public class JwtAuthenticationFilter extends GenericFilterBean { // filtro genérico do Spring

  @Override // filtro chain para passar a requisição adiante
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      HttpServletRequest servletRequest = (HttpServletRequest) request;
      String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);

      if (authorization != null) {
        Authentication credentials = JwtUtils.parseToken(authorization.replaceAll("Bearer ", ""));
        SecurityContextHolder.getContext().setAuthentication(credentials);
      }

      chain.doFilter(request, response); // continua o fluxo da request
    } catch (Throwable t) {
      HttpServletResponse servletResponse = (HttpServletResponse) response;
      servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, t.getMessage());
    }
  }
}