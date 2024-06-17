package com.hotelking.domain.hotel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.hotelking.domain.hotel.vo.Coordinate;
import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Coordinate 값객체 테스트")
class CoordinateTest {

  @Test
  @DisplayName("좌표를 생성한다. - 성공")
  void 좌표_생성_테스트() {
    double lat = 37.2212123213;
    double lng = 127.230123312321;

    Coordinate coordinate = new Coordinate(lat, lng);

    assertThat(coordinate.getLat()).isEqualTo(lat);
    assertThat(coordinate.getLng()).isEqualTo(lng);
  }

  @DisplayName("범위 밖 위도, 경도로 좌표 생성 시 예외 발생 - 실패")
  @ParameterizedTest
  @CsvSource({
      "32,         127.0002",
      "44,         127.0002",
      "33,        123.32142",
      "33,        133.32312"
  })
  void throwExceptionNotRangeLat(double lat, double lng) {
    assertThatThrownBy(() -> new Coordinate(lat, lng))
            .isInstanceOf(HotelkingException.class);
  }
}