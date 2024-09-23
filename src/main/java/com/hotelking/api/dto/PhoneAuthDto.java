package com.hotelking.api.dto;

import com.hotelking.domain.member.PhoneAuth;
import com.hotelking.domain.member.PhoneAuthCode;

public record PhoneAuthDto(String phoneNumber) {

  public PhoneAuth toPhoneAuth(PhoneAuthCode phoneAuthCode) {
    return PhoneAuth.builder()
        .phoneNumber(phoneNumber)
        .authCode(phoneAuthCode)
        .build();
  }
}
