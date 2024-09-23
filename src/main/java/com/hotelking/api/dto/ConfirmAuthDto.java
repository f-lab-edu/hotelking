package com.hotelking.api.dto;

import com.hotelking.domain.member.PhoneAuthCode;
import com.hotelking.domain.member.PhoneNumber;
import lombok.Builder;

@Builder
public record ConfirmAuthDto(
    String token,
    String phoneNumber,
    String authCode
) {

  public PhoneAuthCode toAuthCode() {
    return new PhoneAuthCode(authCode);
  }

  public PhoneNumber toPhoneNumber() {
    return new PhoneNumber(phoneNumber);
  }
}
