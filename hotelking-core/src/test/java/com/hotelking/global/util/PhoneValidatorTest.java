package com.hotelking.global.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.hotelking.util.PhoneNumberValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneValidatorTest {

  @DisplayName("올바른 형식의 휴대번호의 경우 isNotValid -> False 반환  - 성공")
  @ParameterizedTest
  @ValueSource(strings = {
      "010-1234-5678",
      "011-1234-5678",
      "016-123-5678",
      "017-1234-5678",
      "018-1234-5678",
      "019-1234-5678"
  })
  void isValidPhoneNumberTest(String phone) {
    assertThat(PhoneNumberValidator.isNotValidFormat(phone)).isFalse();
  }

  @DisplayName("잘못 형식의 휴대번호의 경우 isNotValid -> True 반환- 성공")
  @ParameterizedTest
  @ValueSource(strings = {
      "02-123-4567",
      "01012345678",
      "010-1234-567"
  })
  void returnFalseNotValidPhoneNumber(String phone) {
    assertThat(PhoneNumberValidator.isNotValidFormat(phone)).isTrue();
  }


}