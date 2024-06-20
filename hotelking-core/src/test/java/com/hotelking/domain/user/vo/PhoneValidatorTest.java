package com.hotelking.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import com.hotelking.global.util.PhoneValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneValidatorTest {

  @DisplayName("유효하지 않은 휴대번호의 경우 PhoneValidator validate 예외 발생 - 실패")
  @ParameterizedTest
  @ValueSource(strings = {
      "02-123-4567",
      "01012345678",
      "010-1234-567"
  })
  void throwExceptionInvalidPhoneNumberParam(String phoneNumber) {
    assertThatThrownBy(() -> PhoneValidator.validate(phoneNumber))
        .isInstanceOf(HotelkingException.class);
  }
}