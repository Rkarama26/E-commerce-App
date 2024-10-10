package com.r_tech.ecommerce.configuration;

import java.util.Arrays;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.r_tech.ecommerce.jwt.JwtValidator;

import jakarta.servlet.http.HttpServletRequest;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.

				sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
								.permitAll().anyRequest().authenticated()

				)

				.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class).csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
					// Enabling CORS
					// overriding method from "CorsConfigurationSource" interface
					// It defines how cors configuration should determined
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

						CorsConfiguration config = new CorsConfiguration();

						// Allowing origin from where this backend can be accessed
						config.setAllowedHeaders(Arrays.asList(

								"http://localhost:3000"));
						// Allowing all type of methods
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("Authorization"));
						config.setExposedHeaders(Arrays.asList("Authorization"));
						config.setMaxAge(3600l);
						return config;
					}
				})).httpBasic(withDefaults()).formLogin(withDefaults());

	//	System.out.println("JwtValidator: Checking JWT token.");

		return http.build();

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
