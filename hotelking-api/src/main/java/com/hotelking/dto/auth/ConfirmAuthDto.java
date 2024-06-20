package com.hotelking.dto.auth;

import com.hotelking.domain.phone.PhoneAuthCode;
import com.hotelking.domain.phone.PhoneNumber;
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
