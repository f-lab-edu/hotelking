package com.hotelking.auth;

import static com.hotelking.auth.JwtProvider.JwtTokenType.AT;
import static com.hotelking.auth.JwtProvider.JwtTokenType.RT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  private final int accessTokenLifetimeSeconds;
  private final int refreshTokenLifetimeSeconds;
  private final Algorithm algorithm;

  enum JwtTokenType {
    AT, RT
  }

  public JwtProvider(
      @Value("${jwt.secret-key}") String secretKey,
      @Value("${jwt.access-life-time-sec}") int accessTokenLifetimeSeconds,
      @Value("${jwt.refresh-life-time-sec}") int refreshTokenLifetimeSeconds
  ) {
    this.algorithm = Algorithm.HMAC512(secretKey);
    this.accessTokenLifetimeSeconds = accessTokenLifetimeSeconds;
    this.refreshTokenLifetimeSeconds = refreshTokenLifetimeSeconds;
  }

  public String issueAccessToken(long userPid) {
    return issueToken(userPid, AT, accessTokenLifetimeSeconds);
  }

  public String issueRefreshToken(long userPid) {
    return issueToken(userPid, RT, refreshTokenLifetimeSeconds);
  }

  public Long parseUserId(String token) {
    return JWT.decode(token).getClaim("sub").asLong();
  }

  private String issueToken(
      long userId,
      JwtTokenType tokenType,
      int lifetimeSeconds
  ) {
    long now = Instant.now().getEpochSecond();
    return JWT.create()
        .withClaim("sub", userId)
        .withClaim("token_type", tokenType.name())
        .withClaim("iat", now)
        .withClaim("exp", now + lifetimeSeconds)
        .sign(algorithm);
  }

  public boolean isValidToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(algorithm).build();
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException e) {
      logger.info("token error {}, token : {}, ", e.getMessage(), token);
    }
    return false;
  }

  public int getRefreshTokenLifetimeSeconds() {
    return refreshTokenLifetimeSeconds;
  }
}
