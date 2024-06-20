package com.hotelking.dto.auth;

import com.hotelking.domain.phone.PhoneAuth;
import com.hotelking.domain.phone.PhoneAuthCode;

public record PhoneAuthDto(String phoneNumber) {

  public PhoneAuth toPhoneAuth(PhoneAuthCode phoneAuthCode) {
    return PhoneAuth.builder()
        .phoneNumber(phoneNumber)
        .authCode(phoneAuthCode)
        .build();
  }
}
