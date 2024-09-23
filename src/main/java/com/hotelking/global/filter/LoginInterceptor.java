package com.hotelking.global.filter;

import static com.hotelking.global.exception.ErrorCode.*;

import com.hotelking.domain.member.UserRepository;
import com.hotelking.global.exception.HotelkingException;
import com.hotelking.global.util.AuthorizationExtractor;
import com.hotelking.global.util.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;

  public LoginInterceptor(TokenProvider tokenProvider, UserRepository userRepository) {
    this.tokenProvider = tokenProvider;
    this.userRepository = userRepository;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    final String token = AuthorizationExtractor.extract(request);
    if (token.isEmpty()) {
      throw new HotelkingException(JWT_TOKEN_NOT_VALID, null);
    }

    final long userPid = tokenProvider.parseUserId(token);
    if (!userRepository.existsById(userPid)) {
      throw new HotelkingException(AUTH_JWT_DELETE_USER, null);
    }

    return true;
  }
}
