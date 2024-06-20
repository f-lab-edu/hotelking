package com.hotelking.application;

import com.hotelking.auth.JwtProvider;
import com.hotelking.domain.user.User;
import com.hotelking.dto.LoginRequestDto;
import com.hotelking.dto.response.JwtTokenResponse;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.repository.UserRepository;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final RedisTemplate<String, String> redisTemplate;
  private static final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:user:";

  public LoginService(
      UserRepository userRepository,
      JwtProvider jwtProvider,
      PasswordEncoder passwordEncoder,
      RedisTemplate<String, String> redisTemplate
  ) {
    this.userRepository = userRepository;
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
    this.redisTemplate = redisTemplate;
  }

  public JwtTokenResponse login(LoginRequestDto loginRequestDto) {
    final User user = findUser(loginRequestDto.userId());
    validatePassword(loginRequestDto, user);
    final String accessToken = jwtProvider.issueAccessToken(user.getId());
    final String refreshToken = getOrIssueRefreshToken(user.getId());
    return JwtTokenResponse.of(accessToken, refreshToken);
  }

  public JwtTokenResponse reIssueAccessToken(String refreshToken) {
    validateIsTokenValid(refreshToken);
    final Long userPid = jwtProvider.parseUserId(refreshToken);
    validateUserByUserPid(userPid);
    validateRefreshTokenIsSaved(userPid);
    return JwtTokenResponse.from(jwtProvider.issueAccessToken(userPid));
  }

  private void validateUserByUserPid(Long userPid) {
    boolean existed = userRepository.existsById(userPid);
    if (!existed) {
      throw new HotelkingException(ErrorCode.USER_NOT_FOUND, null);
    }
  }

  private void validateIsTokenValid(String refreshToken) {
    boolean validToken = jwtProvider.isValidToken(refreshToken);
    if (!validToken) {
      throw new HotelkingException(ErrorCode.JWT_TOKEN_NOT_VALID, null);
    }
  }

  private void validateRefreshTokenIsSaved(Long userId) {
    final String key = generateKey(userId);
    final Boolean hasKey = redisTemplate.hasKey(key);
    if (hasKey == null || !hasKey) {
      throw new HotelkingException(ErrorCode.JWT_RT_TOKEN_EXPIRE, null);
    }
  }

  private User findUser(String userId) {
    return userRepository.findByUserId(userId)
        .orElseThrow(() -> new HotelkingException(ErrorCode.USER_ID_NOT_FOUND, null));
  }

  private void validatePassword(LoginRequestDto loginRequestDto, User user) {
    boolean isNotMatch = !passwordEncoder.matches(loginRequestDto.password(), user.getPassword());
    if (isNotMatch) {
      throw new HotelkingException(ErrorCode.USER_PASSWORD_NOT_MATCH, null);
    }
  }

  private void saveRefreshToken(String key, String token) {
    redisTemplate.opsForValue().set(
        key,
        token,
        jwtProvider.getRefreshTokenLifetimeSeconds(),
        TimeUnit.SECONDS
    );
  }

  private static String generateKey(long userPid) {
    return REFRESH_TOKEN_KEY_PREFIX + userPid;
  }

  private String getOrIssueRefreshToken(long userPid) {
    String key = generateKey(userPid);
    String findRefreshToken = redisTemplate.opsForValue().get(key);

    if (findRefreshToken != null) {
      return findRefreshToken;
    }

    String refreshToken = jwtProvider.issueRefreshToken(userPid);
    saveRefreshToken(key, refreshToken);
    return refreshToken;
  }
}
