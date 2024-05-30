package com.hotelking.auth;

public record PhoneAuthDto(String phoneNumber) {

  public PhoneAuth toPhoneAuth(PhoneAuthCode phoneAuthCode) {
    return PhoneAuth.builder()
        .phoneNumber(phoneNumber)
        .authCode(phoneAuthCode)
        .build();
  }
}
