package com.bijay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Bean
	public BCryptPasswordEncoder getPwdEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);		 
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable().csrf().disable()
		.formLogin()
		.loginPage("/login")
		.successHandler(loginSuccessHandler)
		.and()
		.authorizeRequests()
		.antMatchers("/signup").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
		.antMatchers("/employee/**").hasAnyAuthority("USER")		
		.anyRequest().authenticated()
		.and()
		.logout()
		.logoutSuccessUrl("/login")
		.and()
		.exceptionHandling()
		.accessDeniedPage("/403");
	}
}
