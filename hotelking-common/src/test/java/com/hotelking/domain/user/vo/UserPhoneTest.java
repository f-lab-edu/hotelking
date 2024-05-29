package com.hotelking.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserPhoneTest {

  @Test
  @DisplayName("UserPhone 객체 생성 - 성공")
  void createUserPhone() {
    UserPhone userPhone = new UserPhone("010-1234-5678");
    assertThat(userPhone.getPhoneNumber()).isEqualTo("010-1234-5678");
  }

  @DisplayName("유효하지 않은 휴대번호 포맷으로 UserPhone 객체 생성 시 예외 발생 - 실패")
  @ParameterizedTest
  @ValueSource(strings = {
      "02-123-4567",
      "01012345678",
      "010-1234-567"
  })
  void throwExceptionInvalidPhoneNumberParam(String phoneNumber) {
    assertThatThrownBy(() -> new UserPhone(phoneNumber))
        .isInstanceOf(HotelkingException.class);
  }
}