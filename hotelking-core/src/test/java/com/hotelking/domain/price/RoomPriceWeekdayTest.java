package com.hotelking.domain.price;

import static com.hotelking.utils.RoomPriceFactory.roomPriceElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomPriceWeekdayTest {

  private final RoomPriceElement roomPriceElement = roomPriceElement(10_000L, 0L, "2024-07-07", "11:00:00");

  @Test
  @DisplayName("Builder 를 이용하여 RoomPriceWeekday 생성 - 성공")
  void createRoomPriceWeekDay() {
    RoomPriceWeekday roomPriceWeekday = RoomPriceWeekday.builder()
        .day(RoomPriceWeekType.FRI)
        .prices(List.of(roomPriceElement))
        .build();

    assertThat(roomPriceWeekday.getPrices()).isEqualTo(List.of(roomPriceElement));
    assertThat(roomPriceWeekday.getDay()).isEqualTo(RoomPriceWeekType.FRI);
  }

  @Test
  @DisplayName("prices 가 empty, Null 이거나 day 가 Null 이면 예외 발생 - 실패")
  void notNullTest() {
    assertThatThrownBy(() -> RoomPriceWeekday.builder()
        .day(RoomPriceWeekType.FRI)
        .prices(null)
        .build())
        .isInstanceOf(HotelkingException.class);

    assertThatThrownBy(() -> RoomPriceWeekday.builder()
        .day(RoomPriceWeekType.FRI)
        .prices(List.of())
        .build())
        .isInstanceOf(HotelkingException.class);

    assertThatThrownBy(() -> RoomPriceWeekday.builder()
        .day(null)
        .prices(List.of(roomPriceElement))
        .build())
        .isInstanceOf(HotelkingException.class);
  }
}