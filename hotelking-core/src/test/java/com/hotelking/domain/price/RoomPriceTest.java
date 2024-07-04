package com.hotelking.domain.price;

import static com.hotelking.domain.price.RoomPriceWeekType.MON;
import static com.hotelking.domain.price.RoomPriceWeekType.TUE;
import static com.hotelking.utils.RoomPriceFactory.roomPriceWeekday;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomPriceTest {

  @Test
  @DisplayName("Builder 로 RoomPrice 객체 생성 테스트")
  void createRoomPrice() {
    RoomPrice roomPrice = RoomPrice.builder()
        .weeksOfDay(creteAllDay())
        .build();

    // then
    assertThat(roomPrice.getWeeksOfDay()).isEqualTo(createPriceWeekDay(Arrays.stream(RoomPriceWeekType.values()).toList()));
  }

  @Test
  @DisplayName("날짜가 하나라도 비어있으면 예외 발생 - 실패")
  void throwExceptionWhenNotContainAllWeekDay() {
    assertThatThrownBy(() -> RoomPrice
        .builder()
        .weeksOfDay(createPriceWeekDay(List.of(MON, TUE)))
        .build())
        .isInstanceOf(HotelkingException.class);
  }

  private List<RoomPriceWeekday> createPriceWeekDay(List<RoomPriceWeekType> roomPriceWeekTypes) {
    return roomPriceWeekTypes.stream()
        .map(day -> roomPriceWeekday(
            day,
            100_000L,
            0L,
            "2024-07-07",
            "11:00:00")
        )
        .toList();
  }

  private List<RoomPriceWeekday> creteAllDay() {
    return Arrays.stream(RoomPriceWeekType.values())
        .map(day -> roomPriceWeekday(
            day,
            100_000L,
            0L,
            "2024-07-07",
            "11:00:00")
        )
        .toList();
  }
}