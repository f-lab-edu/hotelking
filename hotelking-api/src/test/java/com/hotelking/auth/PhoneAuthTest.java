package com.hotelking.auth;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneAuthTest {

  @Test
  @DisplayName("Builder 를 이용하여 PhoneVerification 객체 생성 - 성공")
  void builderTest() {
    assertThatCode(() -> PhoneAuth.builder()
        .id(1L)
        .phoneNumber("010-1234-5678")
        .authCode(new PhoneAuthCode("102932"))
        .build())
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("authCode 가 없으면 예외 발생 - 실패")
  void throwExceptionNoVerifyCode() {
    assertThatThrownBy(() -> PhoneAuth.builder()
        .id(1L)
        .phoneNumber("010-1234-5678")
        .authCode(null)
        .build())
        .isInstanceOf(HotelkingException.class);
  }

  @DisplayName("코드 번호가 6자리숫자 아니거나 null, empty 이면 예외 발생 - 실패")
  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(
      strings = {"12345", "123", "abc1"}
  )
  void throwExceptionNotVerifyCodeFormat(String code) {
    assertThatThrownBy(() -> PhoneAuth.builder()
        .id(1L)
        .phoneNumber("010-1234-5678")
        .authCode(new PhoneAuthCode(code))
        .build())
        .isInstanceOf(HotelkingException.class);
  }

  @DisplayName("유효하지 않은 휴대전화번호로 객체 생성 시 예외 발생 - 실패")
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
  void throwExceptionNotValidPhoneNumber(String phoneNumber) {
    assertThatThrownBy(() -> PhoneAuth.builder()
        .id(1L)
        .phoneNumber(phoneNumber)
        .authCode(new PhoneAuthCode("123122"))
        .build())
        .isInstanceOf(HotelkingException.class);
  }
}