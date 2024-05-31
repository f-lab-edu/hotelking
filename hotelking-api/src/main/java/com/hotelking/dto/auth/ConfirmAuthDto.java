package com.hotelking.dto.auth;

import com.hotelking.auth.PhoneAuthCode;
import com.hotelking.auth.PhoneNumber;
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
