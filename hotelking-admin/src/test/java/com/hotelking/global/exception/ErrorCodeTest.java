package com.hotelking.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorCodeTest {

  @Test
  @DisplayName("code 로 ErrorCode 를 찾는다 - 성공")
  void findErrorCodeByCodeMessage() {
    assertThat(ErrorCode.findCode("HOTEL_ADMIN_01")).isEqualTo(ErrorCode.HOTEL_ADMIN_01);
  }

  @Test
  @DisplayName("code 로 ErrorCode 를 찾지못하면 NOT_DEFINED 에러 코드를 반환한다. - 성공")
  void returnNotDefinedErrorCode() {
    assertThat(ErrorCode.findCode("???")).isEqualTo(ErrorCode.NOT_DEFINED);
  }
}