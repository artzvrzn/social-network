package com.artzvrzn.util;

import com.artzvrzn.dto.ProfileDto;
import java.util.Map;

public final class ClaimsUtil {
  private static final String USERNAME = "preferred_username";
  private static final String EMAIL = "email";
  private static final String FIRST_NAME = "given_name";
  private static final String FAMILY_NAME = "family_name";

  private ClaimsUtil() {}

  public static ProfileDto profileDtoFromClaims(Map<String, Object> claims) {
    String username = claims.get(USERNAME).toString();
    String email = claims.get(EMAIL).toString();
    String firstName = claims.get(FIRST_NAME).toString();
    String familyName = claims.get(FAMILY_NAME).toString();
    return ProfileDto.builder()
      .username(username)
      .email(email)
      .name(firstName)
      .familyName(familyName)
      .build();
  }
}
