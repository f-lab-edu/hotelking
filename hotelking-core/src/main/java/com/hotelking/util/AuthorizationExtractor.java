package com.hotelking.util;

import static com.hotelking.exception.ErrorCode.AUTH_JWT_NUL;

import com.hotelking.exception.HotelkingException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AuthorizationExtractor {

  public static String extract(HttpServletRequest request) {
    final String authorization = request.getHeader("Authorization");
    if (Objects.isNull(authorization) || !authorization.startsWith("Bearer")) {
      throw new HotelkingException(AUTH_JWT_NUL, null);
    }
    return authorization.substring(7);
  }

}
