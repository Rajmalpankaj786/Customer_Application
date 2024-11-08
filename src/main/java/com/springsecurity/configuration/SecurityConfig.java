package com.springsecurity.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
	
	@Bean
     public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception{
		
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		
		http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization")); // this need to be configure in cors origin if 
														//any ui domain try to use resource of web service
				return cfg;
			}
		}))
		.authorizeHttpRequests(auth -> {
			auth
			  .requestMatchers(HttpMethod.POST, "/customers").permitAll()
			  .requestMatchers("/hello").permitAll()
			  .requestMatchers(HttpMethod.GET,"/customers","/getAllCustomers").hasRole("ADMIN")
			  .requestMatchers(HttpMethod.GET ,"/customers/**").hasAnyRole("ADMIN","USER")  // what is ** is showing PathVeriable -----
			  .requestMatchers(HttpMethod.PUT ,"/customers/**").hasAnyRole("ADMIN","USER") 
			  .requestMatchers(HttpMethod.DELETE ,"/customers/**").hasAnyRole("ADMIN") 
			  .anyRequest().authenticated();
		})
		.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
				.ignoringRequestMatchers("/notice","/contact","/customers","/customers/**")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		//.csrf(csrf -> csrf.disable())
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
		// this is one of the hashing technique and recommendated one
	}
	
}
