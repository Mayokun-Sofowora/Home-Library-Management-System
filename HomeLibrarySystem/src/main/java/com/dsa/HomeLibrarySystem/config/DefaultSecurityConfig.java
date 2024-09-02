package com.dsa.HomeLibrarySystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Recommended encoder
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for simplicity (not recommended for production)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() // Allow access to static resources and login page
                                .requestMatchers("/dashboard", "/index", "/members", "/loans", "add-member", "add-loan", "add-book", "add-journal", "book-details", "journal-details").authenticated() // Require authentication for these endpoints
                                .anyRequest().authenticated() // Ensure all other requests are authenticated
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Custom login page
                                .defaultSuccessUrl("/dashboard") // Redirect to /dashboard after successful login
                                .permitAll() // Allow all to access the login page
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // URL to trigger logout
                                .logoutSuccessUrl("/login?logout") // Redirect to login page with logout message
                                .permitAll() // Allow all to access logout functionality
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }

}
