package com.hotelking.global.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneNumberValidatorTest {

  @DisplayName("유효한 phoneNumber -> isNotValid False 반환")
  @ParameterizedTest
  @ValueSource(
      strings = {
          "010-1234-5678",
          "011-1234-5678",
          "016-123-5678",
          "017-1234-5678",
          "018-1234-5678",
          "019-1234-5678"
      }
  )
  void isNotValidFormat(String number) {
    assertThat(PhoneNumberValidator.isNotValidFormat(number)).isFalse();
  }

  @DisplayName("유효하지 않은 phoneNumber -> isNotValid True 반환")
  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(
      strings = {
          "010-123-45678",
          "011-12345-678",
          "02-123-4567",
          "01012345678",
          "010-1234-567"
      }
  )
  void returnTrueNotValidFormat(String number) {
    assertThat(PhoneNumberValidator.isNotValidFormat(number)).isTrue();
  }
}