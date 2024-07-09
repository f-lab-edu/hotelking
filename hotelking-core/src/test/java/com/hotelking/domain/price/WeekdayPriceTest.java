package com.hotelking.domain.price;

import static com.hotelking.utils.RoomPriceFactory.priceAndDiscount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekdayPriceTest {

  @Test
  @DisplayName("Builder 를 이용하여 RoomPriceWeekday 생성 - 성공")
  void createRoomPriceWeekDay() {
    final PriceAndDiscount priceAndDiscount = priceAndDiscount(10000L, 0);
    WeekdayPrice weekdayPrice = WeekdayPrice.builder()
        .day(WeekType.FRIDAY)
        .price(priceAndDiscount)
        .build();

    assertThat(weekdayPrice.calPrice()).isEqualTo(priceAndDiscount);
    assertThat(weekdayPrice.getDay()).isEqualTo(WeekType.FRIDAY);
  }

  @Test
  @DisplayName("prices 가 empty, Null 이거나 day 가 Null 이면 예외 발생 - 실패")
  void notNullTest() {
    assertThatThrownBy(() -> WeekdayPrice.builder()
        .day(WeekType.FRIDAY)
        .price(null)
        .build())
        .isInstanceOf(HotelkingException.class);

    assertThatThrownBy(() -> WeekdayPrice.builder()
        .day(null)
        .build())
        .isInstanceOf(HotelkingException.class);

    assertThatThrownBy(() -> WeekdayPrice.builder()
        .day(null)
        .build())
        .isInstanceOf(HotelkingException.class);
  }
}