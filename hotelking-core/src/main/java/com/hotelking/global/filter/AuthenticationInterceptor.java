package com.hotelking.global.filter;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.global.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

  private final TokenProvider tokenProvider;

  public AuthenticationInterceptor(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    final String authorization = request.getHeader("Authorization");

    if (authorization == null || !authorization.startsWith("Bearer ")) {
      throw new HotelkingException(ErrorCode.REQ_ORDER_DATE, null);
    }

    String token = authorization.substring(7);
    if (token == null) {

    }

    return true;
  }
}
