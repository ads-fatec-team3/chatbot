package br.gov.sp.fatec.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Classe de configuração mais detalhada
//Agora não precisa d usuário e senha para acessar a aplicação pq tudo será protegido por anotação
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//Onde for anotado tá seguro onde não for anotado não está seguro
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //adicionando o meu filtro antes do UsernamePasswordAuthenticationFilter do Spring
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //gerenciamento de sessão: stateless
    }

    @Bean //Disponibiliza para autowired
    public PasswordEncoder passWordEncoderBean(){ //metodo padrão do spring p/ codificar a senha do user
        return new BCryptPasswordEncoder();
    }

    @Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {//Pra login esse serviço será usado
		auth.userDetailsService(userDetailsService);
    }
    
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
