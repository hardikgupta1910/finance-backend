package com.finance.backend.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private jwtFilter jwtFilter;
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
				.csrf(csrf -> csrf.disable())
				
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				
				
				.formLogin(form -> form.disable())
				.httpBasic(basic -> basic.disable())
				
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/auth/**",
								"/swagger-ui/**",
								"/v3/api-docs/**",
								"/health"
						).permitAll()
						.anyRequest().authenticated()
				)
				
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	
}
