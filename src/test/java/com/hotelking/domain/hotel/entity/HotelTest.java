package com.hotelking.domain.hotel.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.hotelking.global.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Hotel 엔티티 테스트")
class HotelTest {
  @Test
  @DisplayName("Builder 로 Hotel 객체 만들기 - 성공")
  void creatHotel() {
    assertThatCode(() -> Hotel.builder()
        .name("경복궁호텔")
        .address("서울특별시 종로구 종로 1-1 경복궁")
        .lat(37.12321)
        .lng(127.123213)
        .build())
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @DisplayName("Hotel 객체 생성 시 name, address 가 null 또는 blank 이면 예외발생 - 실패")
  @CsvSource(value = {
      "null,         서울특별시 종로구 종로 1-1 경복궁",
      "'  ',        서울특별시 종로구 종로 1-1 경복궁",
      "경복궁 호텔, null",
      "경복궁 호텔, '   '"
  }, nullValues = "null")
  void notNullOrBlankNameAndAddress(String name, String address) {
    assertThatThrownBy(() -> Hotel.builder()
        .name(name)
        .address(address)
        .lat(37.12321)
        .lng(127.123213)
        .build())
        .isInstanceOf(HotelkingException.class);
  }
}