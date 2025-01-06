package com.edgaritzak.imageBoard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
    return httpSecurity
            .authorizeHttpRequests(auth -> 
              auth
              .requestMatchers("/admin").authenticated()
              .requestMatchers("/admin/**").authenticated()
              .anyRequest().permitAll()
            )
            .formLogin(login -> 
              login
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> 
              logout
                .invalidateHttpSession(true)
                .permitAll()
            )
            .build();
  } 
}
