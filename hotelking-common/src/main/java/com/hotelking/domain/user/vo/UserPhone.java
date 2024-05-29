package com.hotelking.domain.user.vo;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.global.util.PhoneValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPhone {

  @Column(name = "user_phone", nullable = false)
  private String phoneNumber;

  public UserPhone(String phoneNumber) {
    if (PhoneValidator.isNotValidPhoneNumber(phoneNumber)) {
      throw new HotelkingException(ErrorCode.USER_INVALID_PARAM_PHONE, null);
    }
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
