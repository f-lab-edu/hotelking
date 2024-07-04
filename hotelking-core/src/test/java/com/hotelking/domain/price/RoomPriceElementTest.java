package com.hotelking.domain.price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomPriceElementTest {

  private static final String dateStr = "2024-07-21";

  @Test
  @DisplayName("RoomPriceElement 객체 생성 - 성공")
  void createRoomPriceElement() {
    PriceAndDiscount defaultPrice = PriceAndDiscount.builder()
        .price(10000L)
        .discountAmount(0L)
        .build();

    HashMap<String, CustomPrice> customPriceHashMap = new HashMap<>();
    CustomPrice customPrice = CustomPrice.builder()
        .defaultPrice(defaultPrice)
        .build();
    customPriceHashMap.put(dateStr, customPrice);

    RoomPriceElement roomPriceElement = RoomPriceElement.builder()
        .defaultPrice(defaultPrice)
        .datePrices(List.of(customPriceHashMap))
        .build();

    assertThat(roomPriceElement.getDatePrices()).isEqualTo(List.of(customPriceHashMap));
    assertThat(roomPriceElement.getDefaultPrice()).isEqualTo(defaultPrice);
  }

  @Test
  @DisplayName("RoomPriceElement 에서 defaultPrice 가 없으면 예외 발생 - 실패")
  void throwExceptionDefaultPriceIsNull() {
    assertThatThrownBy(
        () -> RoomPriceElement.builder()
            .defaultPrice(null)
            .build())
        .isInstanceOf(HotelkingException.class);
  }
}