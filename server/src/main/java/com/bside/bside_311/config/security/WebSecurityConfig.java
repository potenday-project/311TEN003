package com.bside.bside_311.config.security;

import com.bside.bside_311.config.filter.JwtAuthFilter;
import com.bside.bside_311.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
//  private final AccessTokenAuthenticationFilter authenticationFilter;



  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    Class<UsernamePasswordAuthenticationFilter> usernamePasswordAuthFilter = UsernamePasswordAuthenticationFilter.class;
    http.cors();
    Object jwtUtil;
    http.csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new AuthEntryPointJwt());
    http.addFilterBefore(jwtAuthFilter, usernamePasswordAuthFilter);

//    http.authorizeHttpRequests()
//        .requestMatchers(HttpMethod.POST, "/session").permitAll()
//        .requestMatchers(HttpMethod.POST, "/admin/session").permitAll()
//        .requestMatchers(HttpMethod.POST, "/users").permitAll()
//        .requestMatchers(HttpMethod.GET, "/categories").permitAll()
//        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
//        .requestMatchers(HttpMethod.GET, "/backdoor/**").permitAll()
//        .anyRequest().authenticated();

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
//    return
  }
}
