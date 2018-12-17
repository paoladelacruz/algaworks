package com.example.algamoneyapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SystemConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	auth.inMemoryAuthentication()
		.withUser("admin")
		.password("admin")
		.roles("ROLE");
			
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/categorias").permitAll() //Posso deixar uma requisicao aberta par Qualquer um ingressar
			.anyRequest().authenticated() //Qualquer requisicao precisa estar autenticada
			.and()
			.httpBasic() //Tipo de Autenticacao
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Nossa API rest não mantenha estado de nada
			.and()
			.csrf().disable();
	}
}
