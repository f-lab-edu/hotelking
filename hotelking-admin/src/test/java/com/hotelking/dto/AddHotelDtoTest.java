package com.hotelking.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

import com.hotelking.domain.hotel.Hotel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AddHotelDto 테스트")
class AddHotelDtoTest {

  @Test
  @DisplayName("AddHotelDto 에서 Hotel Entity 변환 - 성공")
  void toEntityTest() {
    AddHotelDto addHotelDto = new AddHotelDto("경복궁호텔", "경복궁호텔주소", 38.12321, 127.23122);
    Hotel hotel = addHotelDto.toEntity();

    assertThat(hotel.getAddress()).isEqualTo(addHotelDto.address());
    assertThat(hotel.getName()).isEqualTo(addHotelDto.name());
    assertThat(hotel.getCoordinate().getLat()).isEqualTo(addHotelDto.lat());
    assertThat(hotel.getCoordinate().getLng()).isEqualTo(addHotelDto.lng());
  }
}