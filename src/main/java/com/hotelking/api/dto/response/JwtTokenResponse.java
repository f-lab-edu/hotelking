package com.hotelking.api.dto.response;

public record JwtTokenResponse(String accessToken, String refreshToken) {

  public static JwtTokenResponse of(String accessToken, String refreshToken) {
    return new JwtTokenResponse(accessToken, refreshToken);
  }

  public static JwtTokenResponse from(String accessToken) {
    return new JwtTokenResponse(accessToken, null);
  }
}
