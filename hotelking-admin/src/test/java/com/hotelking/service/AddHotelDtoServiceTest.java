package com.hotelking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hotelking.domain.HotelRepository;
import com.hotelking.domain.hotel.Hotel;
import com.hotelking.dto.AddHotelDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
@DisplayName("호텔 추가하기 서비스 테스트")
class AddHotelDtoServiceTest {


  @InjectMocks
  AddHotelService addHotelService;

  @Mock
  HotelRepository hotelRepository;

  @Test
  @DisplayName("호텔 추가 하기 - 성공")
  void addHotelTest() {
    AddHotelDto addHotel = new AddHotelDto("경복궁호텔", "경복궁호텔주소", 37.123213, 128.231323);

    assertThatCode(() -> addHotelService.registerHotel(addHotel)).doesNotThrowAnyException();
    verify(hotelRepository, times(1)).save(any());
  }
}