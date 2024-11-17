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

				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**",
						"/swagger-ui.html", "localhost:8080").permitAll().anyRequest().authenticated()

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
						config.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // Allowing
																										// frontend
																										// origin
						config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Specify
																											// allowed
																											// methods
						config.setAllowCredentials(true); // Allow credentials (important for cookies/auth)
						config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Allow necessary
																									// headers
						config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type")); // Expose headers
						config.setMaxAge(3600L); // Cache the CORS settings for 1 hour
						return config;
					}
				})).httpBasic(withDefaults()).formLogin(withDefaults());

		// System.out.println("JwtValidator: Checking JWT token.");

		return http.build();

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
