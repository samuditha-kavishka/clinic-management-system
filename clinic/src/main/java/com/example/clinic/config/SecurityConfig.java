package com.example.clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public URLs
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()

                        // Role-based access (අවශ්‍ය නම්)
                        // .requestMatchers("/admin/**").hasRole("ADMIN")
                        // .requestMatchers("/doctor/**").hasRole("DOCTOR")
                        // .requestMatchers("/patient/**").hasRole("PATIENT")

                        // All other requests need authentication
                        .anyRequest().authenticated()
                )

                // Login configuration
                .formLogin(form -> form
                        .loginPage("/login")                // Custom login page URL
                        .loginProcessingUrl("/login")       // Spring Security login processing URL
                        .usernameParameter("username")      // Form username parameter name
                        .passwordParameter("password")      // Form password parameter name
                        .defaultSuccessUrl("/dashboard", true)  // Login success redirect
                        .failureUrl("/login?error=true")    // Login failure redirect
                        .permitAll()
                )

                // Logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")               // Logout URL
                        .logoutSuccessUrl("/login?logout=true")  // After logout redirect
                        .invalidateHttpSession(true)        // Session clear
                        .deleteCookies("JSESSIONID")        // Cookies delete
                        .permitAll()
                )

                // Remember Me
                .rememberMe(remember -> remember
                        .rememberMeParameter("remember-me")  // Remember me parameter name
                        .tokenValiditySeconds(86400)         // 24 hours
                        .key("clinicAppKey")                 // Unique key
                )

                // Exception handling
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")  // Access denied page
                )

                // CSRF (Development එකේ disable කරන්න)
                .csrf(csrf -> csrf.disable());          // Production එකේ enable කරන්න

        return http.build();
    }
}