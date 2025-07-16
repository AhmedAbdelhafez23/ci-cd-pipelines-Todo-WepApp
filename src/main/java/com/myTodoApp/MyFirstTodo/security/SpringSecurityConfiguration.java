package com.myTodoApp.MyFirstTodo.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	//LDAP or Database
	//In Memory 
	
	//InMemoryUserDetailsManager
	//InMemoryUserDetailsManager(UserDetails... users)
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		
		UserDetails userDetails1 = createNewUser("Ahmed", "dummy");
		UserDetails userDetails2 = createNewUser("Abdul", "dummydummy");
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder
		= input -> passwordEncoder().encode(input);

		UserDetails userDetails = User.builder()
									.passwordEncoder(passwordEncoder)
									.username(username)
									.password(password)
									.roles("USER","ADMIN")
									.build();
		return userDetails;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//All URLs are protected
	//A login form is shown for unauthorized requests
	//CSRF disable
	//Frames
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		http.formLogin(withDefaults());
		
		http.csrf(csrf -> csrf.disable());
		// OR
		// http.csrf(AbstractHttpConfigurer::disable);

		http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // Starting from SB 3.1.x
		
		return http.build();
	}

	// @Bean
	// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	// 	http
	// 			.authorizeHttpRequests(auth -> auth
	// 					.requestMatchers("/login", "/resources/**", "/css/**", "/js/**").permitAll() // Öffentliche
	// 																									// Pfade
	// 					.anyRequest().authenticated() // Alle anderen Pfade sind geschützt
	// 			)
	// 			.formLogin(form -> form
	// 					.loginPage("/login") // Optional: Eigene Login-Seite
	// 					.defaultSuccessUrl("/list-todos", true) // Weiterleitung nach erfolgreichem Login
	// 					.permitAll())
	// 			.logout(logout -> logout.permitAll()) // Logout erlauben
	// 			.csrf(csrf -> csrf.disable()) // CSRF deaktivieren (wenn nötig)
	// 			.headers(headers -> headers.frameOptions(frame -> frame.disable())); // Frames erlauben für H2-Console

	// 	return http.build();
	// }
	
	
	
	
	
}