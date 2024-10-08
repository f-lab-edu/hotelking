package com.hotelking.domain.member;

import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;
import com.hotelking.global.util.PhoneNumberValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {

  @Column(name = "phone_number", updatable = false)
  private String number;

  public PhoneNumber(String number) {
    verifyPhoneNumberFormat(number);
    this.number = number;
  }

  private void verifyPhoneNumberFormat(String number) {
    if (PhoneNumberValidator.isNotValidFormat(number)) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_NUMBER, null);
    }
  }

  public boolean isSame(PhoneNumber phoneNumber) {
    return number.equals(phoneNumber.number);
  }

}
