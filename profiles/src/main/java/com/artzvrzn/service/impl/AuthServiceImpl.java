package com.artzvrzn.service.impl;

import com.artzvrzn.service.AuthService;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

  @Override
  public Map<String, Object> getClaims() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.debug(authentication);
    if (authentication == null) {
      throw new IllegalStateException("no authentication has been found");
    }
    Object principal = authentication.getPrincipal();
    if (principal == null) {
      throw new IllegalStateException("no principal has been found");
    }
    return ((Jwt) principal).getClaims();
  }

  @Override
  public String getAuthenticatedUser() {
    Map<String, Object> claims = getClaims();
    String user = claims.get("sub").toString();
    log.debug(user);
    if (user == null) {
      throw new IllegalStateException("token does not contain subject");
    }
    return user;
  }
}
