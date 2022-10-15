package io.rachidassouani.eshopbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.permitAll()
		.and()
			.logout()
			.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");	
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
	}
}
