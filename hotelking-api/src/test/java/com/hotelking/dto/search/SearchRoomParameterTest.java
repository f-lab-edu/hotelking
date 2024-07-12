package com.hotelking.dto.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.exception.HotelkingException;
import com.hotelking.query.condition.SearchRoomParameter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

class SearchRoomParameterTest {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @ParameterizedTest
  @DisplayName("빈 방 검색 조건 생성 시 hotelId 와 checkIn 이 없으면 예외 발생 - 실패")
  @CsvSource(value = {
      "1, null",
      "0, 2024-06-24"
  }, nullValues = { "null" })
  void throwExceptionWhenHotelIdOrSearchIdIsNull(
      long hotelId,
      LocalDate checkIn
  ) {
    assertThatThrownBy(
        () -> new SearchRoomParameter(
            hotelId,
            checkIn,
            null,
            null
        )
    ).isInstanceOf(HotelkingException.class);
  }

  @ParameterizedTest
  @DisplayName("검색 타입이 Null 이거나 STAY 가 아닌 경우 반드시 checkOut 은 checkIn + 1day 로 만들어짐 - 성공")
  @EnumSource(value = ReservationType.class, names = {"BOTH", "RENT"})
  @NullSource
  void checkOutIsCheckInPlusOneDayWhenRent(ReservationType type) {
    LocalDate checkIn = LocalDate.parse("2024-07-01", formatter);
    SearchRoomParameter searchRoomParameter = new SearchRoomParameter(
        1L,
        checkIn,
        null,
        type
    );

    assertThat(searchRoomParameter.checkOut()).isEqualTo(checkIn.plusDays(1L));
  }

  @ParameterizedTest
  @DisplayName("검색 타입이 STAY 인 경우 checkIn 은 checkOut 앞서지 않으면 에러 발생")
  @CsvSource(value = {
      "2024-07-01, 2024-06-28",
      "2024-01-01, 2023-12-30"
  })
  void stayDateOrderCheck(String checkIn, String checkOut) {
    assertThatThrownBy(() -> new SearchRoomParameter(
        1L,
        LocalDate.parse(checkIn, formatter),
        LocalDate.parse(checkOut, formatter),
        ReservationType.STAY
    )).isInstanceOf(HotelkingException.class);

  }
}