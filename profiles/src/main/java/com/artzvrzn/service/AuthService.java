package com.artzvrzn.service;

import java.util.Map;

public interface AuthService {

  Map<String, Object> getClaims();

  String getAuthenticatedUser();
}
