package com.hotelking.auth;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

  @Test
  @DisplayName("인증확인 여부시, 이미 인증된 경우 예외 발생 - 실패")
  void throwExceptionAlreadyConfirm() {
    PhoneAuth phoneAuth = PhoneAuth.builder()
        .id(1L)
        .phoneNumber("010-1234-5678")
        .authCode(new PhoneAuthCode("102932"))
        .build();
    PhoneAuthCode authCode = new PhoneAuthCode("102932");
    PhoneNumber phoneNumber = new PhoneNumber("010-1234-5678");
    phoneAuth.confirm();

    assertThatThrownBy(() -> phoneAuth.checkConfirmable(authCode, phoneNumber))
        .isInstanceOf(HotelkingException.class);
  }

  @DisplayName("인증확인 여부시, 인증번호 또는 휴대번호가 다른 경우 예외발생 - 실패")
  @ParameterizedTest
  @CsvSource(
      value = {
          "102932, 102932, 010-1234-5678, 010-1234-0000",
          "102932, 111111, 010-1234-5678, 010-1234-5678",
          "102932, 111111, 010-1234-5678, 010-1234-0000"
      }
  )
  void throwExceptionNotEqualAuthCodeOrPhoneNumber(
      String givenCode,
      String authCodeVal,
      String givenPhone,
      String phoneNumberVal
  ) {
    PhoneAuth phoneAuth = PhoneAuth.builder()
        .id(1L)
        .phoneNumber(givenPhone)
        .authCode(new PhoneAuthCode(givenCode))
        .build();

    PhoneAuthCode authCode = new PhoneAuthCode(authCodeVal);
    PhoneNumber phoneNumber = new PhoneNumber(phoneNumberVal);
    phoneAuth.confirm();

    assertThatThrownBy(() -> phoneAuth.checkConfirmable(authCode, phoneNumber))
        .isInstanceOf(HotelkingException.class);
  }

}
