package com.hotelking.domain.member;

import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneAuthCode {

  public static final int VERIFY_CODE_LENGTH = 6;
  public static final String VERIFY_CODE_PATTERN = "\\d+";

  @Column(name = "auth_code", length = 6, nullable = false, updatable = false)
  private String value;

  public String getValue() {
    return value;
  }

  public PhoneAuthCode(final String value) {
    verifyCodeFormat(value);
    this.value = value;
  }

  private void verifyCodeFormat(final String value) {
    if (value == null) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_CODE, null);
    }

    if (value.length() != VERIFY_CODE_LENGTH || !value.matches(VERIFY_CODE_PATTERN)) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_FORMAT_1, null);
    }
  }

  public boolean isSame(PhoneAuthCode phoneAuthCode) {
    return value.equals(phoneAuthCode.value);
  }
}
