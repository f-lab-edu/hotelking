package com.hotelking.dto.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SearchRoomParameterTest {

  @ParameterizedTest
  @DisplayName("객체 생성 시 hotelId 와 checkIn 이 없으면 예외 발생 - 실패")
  @CsvSource(value = {
      "1, null",
      "0, 2024-06-24"
  }, nullValues = { "null" })
  void throwExceptionWhenHotelIdOrSearchIdIsNull(long hotelId, String checkIn) {
    // test
  }
}