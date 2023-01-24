package com.artzvrzn;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
      .authorizeExchange()
      //ALLOWING REGISTER API FOR DIRECT ACCESS
//      .pathMatchers("/user/api/v1/register").permitAll()
      //ALL OTHER APIS ARE AUTHENTICATED
//      .anyExchange().authenticated()
      .anyExchange().authenticated()
      .and()
//      .oauth2Login()
//      .and()
      .oauth2ResourceServer()
      .jwt();
    return http.build();
  }
}