package br.gov.sp.fatec.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// Classe de configuração mais detalhada
// Agora não precisa de usuário e senha para acessar a aplicação, já que tudo será protegido por anotação
@EnableWebSecurity
// Onde for anotado está seguro e onde não for anotado não está seguro
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthService authService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      // desabilitar CSRF
      .csrf().disable()
      // habilitar o filtro de autenticação por jwt antes da autenticação de usuário
      .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
      // habilitar cors
      .cors()
      .and()
      .authorizeRequests()
      // permitir acesso à rota de autenticação
      .antMatchers("/api/auth/*").permitAll()
      // permitir acesso somente a usuários autenticados e com as seguintes roles
      .antMatchers("/api/**").hasAnyRole("STUDENT", "DIRECTOR", "INSTRUCTOR", "ATTENDANT")
      .and()
      // gerenciamento de sessão stateless
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  // Método padrão do spring para codificar a senha do usuário
  @Bean // Disponibiliza para autowired
  public PasswordEncoder passwordEncoderBean() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  // Pra login, esse serviço será usado
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authService);
  }
}
