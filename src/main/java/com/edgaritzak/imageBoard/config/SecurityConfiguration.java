package com.edgaritzak.imageBoard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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

  // @Bean
  // public UserDetailsService userDetails(){
  //   UserDetails user = User.withUsername("itzak")
  //       .password("{noop}pw1")
  //       .roles("ADMIN")
  //       .build();
  //   return new InMemoryUserDetailsManager(user);
  // }
}
