package com.hotelking.domain.user.vo;

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
    PhoneValidator.isValidPhoneNumber(phoneNumber);
    this.phoneNumber = phoneNumber;
  }
}
