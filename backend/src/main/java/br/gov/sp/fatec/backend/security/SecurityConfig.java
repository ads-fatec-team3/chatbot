package br.gov.sp.fatec.backend.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

//Classe de configuração mais detalhada
//Agora não precisa d usuário e senha para acessar a aplicação pq tudo será protegido por anotação
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//Onde for anotado tá seguro onde não for anotado não está seguro
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and()//desabilitando o csrf: agora páginas de fora do spring podem acessar o backend
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //gerenciamento de sessão: stateless
    }
}
