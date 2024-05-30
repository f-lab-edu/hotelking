package com.hotelking.dto.auth;

import com.hotelking.auth.PhoneAuth;
import com.hotelking.auth.PhoneAuthCode;

public record PhoneAuthDto(String phoneNumber) {

  public PhoneAuth toPhoneAuth(PhoneAuthCode phoneAuthCode) {
    return PhoneAuth.builder()
        .phoneNumber(phoneNumber)
        .authCode(phoneAuthCode)
        .build();
  }
}
