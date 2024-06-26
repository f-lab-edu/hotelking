package com.hotelking.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenProviderTest {

  private final String secret = "test-secret-key";
  private final int atlt = 1;
  private final int rtlt = 2;
  private final TokenProvider tokenProvider = new TokenProvider(secret, atlt, rtlt);

  @Test
  @DisplayName("accessToken 발행 성공")
  void testIssueAccessToken() {
    String accessToken = tokenProvider.issueAccessToken(1L);
    assertThat(accessToken).isNotBlank();
  }

  @Test
  @DisplayName("refreshToken 발행 성공")
  void testIssueRefreshToken() {
    String accessToken = tokenProvider.issueRefreshToken(1L);
    assertThat(accessToken).isNotBlank();
  }

  @Test
  @DisplayName("token 에서 sub 파싱")
  void parseSubClaimFromToken() {
    String accessToken = tokenProvider.issueAccessToken(1L);
    long userId = tokenProvider.parseUserId(accessToken);
    assertThat(userId).isEqualTo(1L);
  }

  @Test
  @DisplayName("토큰 검증 시 시간 만료되면 유효검사 결과 False")
  void returnFalseExpiredToken() throws InterruptedException {
    String accessToken = tokenProvider.issueAccessToken(1L);
    Thread.sleep(1100);
    assertThat(tokenProvider.isValidToken(accessToken)).isFalse();
  }
}
