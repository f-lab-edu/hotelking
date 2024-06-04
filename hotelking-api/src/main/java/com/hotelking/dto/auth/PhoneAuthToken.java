package com.hotelking.dto.auth;

public record PhoneAuthToken(String token) {

  public static PhoneAuthToken from(String token) {
    return new PhoneAuthToken(token);
  }
}
