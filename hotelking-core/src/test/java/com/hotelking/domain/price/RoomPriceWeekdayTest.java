package com.hotelking.domain.price;

import static com.hotelking.utils.RoomPriceFactory.datePrice;
import static com.hotelking.utils.RoomPriceFactory.priceAndDiscount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomPriceWeekdayTest {

  @Test
  @DisplayName("Builder 를 이용하여 RoomPriceWeekday 생성 - 성공")
  void createRoomPriceWeekDay() {
    final List<HashMap<String, CustomPrice>> datePrices = List.of(
        datePrice(10000L, 0L, "2024-07-07", "11:00:00"));
    final PriceAndDiscount priceAndDiscount = priceAndDiscount(10000L, 0);
    RoomPriceWeekday roomPriceWeekday = RoomPriceWeekday.builder()
        .day(RoomPriceWeekType.FRIDAY)
        .defaultPrice(priceAndDiscount)
        .customDefaultPrice(null)
        .customDatePrices(datePrices)
        .build();

    assertThat(roomPriceWeekday.getDefaultPrice()).isEqualTo(priceAndDiscount);
    assertThat(roomPriceWeekday.getCustomDatePrices()).isEqualTo(datePrices);
    assertThat(roomPriceWeekday.getDay()).isEqualTo(RoomPriceWeekType.FRIDAY);
  }

  @Test
  @DisplayName("prices 가 empty, Null 이거나 day 가 Null 이면 예외 발생 - 실패")
  void notNullTest() {
    assertThatThrownBy(() -> RoomPriceWeekday.builder()
        .day(RoomPriceWeekType.FRIDAY)
        .customDatePrices(null)
        .build())
        .isInstanceOf(HotelkingException.class);

    assertThatThrownBy(() -> RoomPriceWeekday.builder()
        .day(RoomPriceWeekType.FRIDAY)
        .customDatePrices(List.of())
        .build())
        .isInstanceOf(HotelkingException.class);

    assertThatThrownBy(() -> RoomPriceWeekday.builder()
        .day(null)
        .customDatePrices(List.of())
        .build())
        .isInstanceOf(HotelkingException.class);
  }
}