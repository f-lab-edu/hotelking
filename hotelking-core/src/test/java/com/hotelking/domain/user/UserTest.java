package com.hotelking.domain.user;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UserTest {

  @Test
  @DisplayName("Builder 이용 User 객체 생성 - 성공")
  void createUserSuccess() {
    assertThatCode(() -> User.builder()
        .id(1L)
        .userId("user123")
        .password("password123")
        .phoneNumber("010-1234-5678")
        .build())
        .doesNotThrowAnyException();
  }

  @DisplayName("userId 또는 password 가 null 이거나 empty 일 경우 객체 생성 시 예외 던짐 - 실패")
  @ParameterizedTest
  @CsvSource(value = {
      "null, password123",
      "'', password123",
      "user123, null",
      "user123, ''",
      "null, null",
      "'', ''"
  }, nullValues = "null")
  void throwExceptionInvalidParam(String userId, String password) {
    assertThatThrownBy(() -> User.builder()
        .id(1L)
        .userId(userId)
        .password(password)
        .phoneNumber("010-1234-5678")
        .build())
        .isInstanceOf(HotelkingException.class);
  }
}
