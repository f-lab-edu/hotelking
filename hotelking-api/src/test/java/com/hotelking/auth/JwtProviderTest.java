package com.hotelking.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

  private final String secret = "test-secret-key";
  private final int atlt = 1;
  private final int rtlt = 2;
  private final JwtProvider jwtProvider = new JwtProvider(secret, atlt, rtlt);

  @Test
  @DisplayName("accessToken 발행 성공")
  void testIssueAccessToken() {
    String accessToken = jwtProvider.issueAccessToken(1L);
    assertThat(accessToken).isNotBlank();
  }

  @Test
  @DisplayName("refreshToken 발행 성공")
  void testIssueRefreshToken() {
    String accessToken = jwtProvider.issueRefreshToken(1L);
    assertThat(accessToken).isNotBlank();
  }

  @Test
  @DisplayName("token 에서 sub 파싱")
  void parseSubClaimFromToken() {
    String accessToken = jwtProvider.issueAccessToken(1L);
    long userId = jwtProvider.parseUserId(accessToken);
    assertThat(userId).isEqualTo(1L);
  }

  @Test
  @DisplayName("토큰 검증 시 시간 만료되면 유효검사 결과 False")
  void returnFalseExpiredToken() throws InterruptedException {
    String accessToken = jwtProvider.issueAccessToken(1L);
    Thread.sleep(1100);
    assertThat(jwtProvider.isValidToken(accessToken)).isFalse();
  }
}
