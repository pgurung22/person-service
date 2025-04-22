package com.glue.person_service.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.glue.person_service.util.Constants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //to make the post request work
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        ) ) //for h2 rendering
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/person/save").hasRole(ADMIN)
                                .requestMatchers("/api/v1/getPeople").hasAnyRole(ADMIN)
                                .requestMatchers("/api/v1/update/**").hasAnyRole(ADMIN)
                                .requestMatchers("/api/v1/delete/**").hasAnyRole(ADMIN)
                                .requestMatchers("/api/v1/filter").hasAnyRole(ADMIN,GUEST)
                                .requestMatchers("/actuator/**").hasRole(ADMIN)
                                .anyRequest().authenticated() // Require authentication for all requests
                )


                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) //wrong credentials while calling the service
                                .accessDeniedHandler(new CustomAccessDeniedHandler())) //when guest is trying to call endpoints he is unauthorized to call
                .httpBasic(Customizer.withDefaults()); // Enable basic authentication

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {  //in-memory password and username manager

        UserDetails adminUser = User.builder()
                .username(GUEST)
                .password(passwordEncoder().encode(PASSWORD))
                .roles(GUEST)
                .build();
        UserDetails guestUser = User.builder()
                .username(ADMIN)
                .password(passwordEncoder().encode(PASSWORD))
                .roles(ADMIN)
                .build();
        return new InMemoryUserDetailsManager(adminUser, guestUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
