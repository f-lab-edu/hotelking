package com.hotelking.domain.price;

import static com.hotelking.utils.RoomPriceFactory.priceAndDiscount;
import static com.hotelking.utils.RoomPriceFactory.priceAndDiscountKey;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.hotelking.exception.HotelkingException;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomPriceTest {

  final PriceAndDiscount priceAndDiscount = priceAndDiscount(100_000L, 0L);
  final HashMap<String, PriceAndDiscount> timePriceMap = priceAndDiscountKey(100_000L, 0L, "11:00:00");

  @Test
  @DisplayName("CustomPrice 생성 테스트 - 성공")
  void createCustomPrice() {
    CustomPrice customPrice = CustomPrice.builder()
        .defaultPrice(priceAndDiscount)
        .timesPrice(timePriceMap)
        .build();

    assertThat(customPrice.getDefaultPrice()).isEqualTo(priceAndDiscount);
    assertThat(customPrice.getTimesPrice()).isEqualTo(timePriceMap);
  }

  @Test
  @DisplayName("CustomPrice 생성 시 DefaultPrice 없으면 예외 발생 - 실패")
  void throwExceptionWhenDefaultPriceIsNull() {
    assertThatThrownBy(() -> CustomPrice.builder()
        .defaultPrice(null)
        .timesPrice(timePriceMap)
        .build())
        .isInstanceOf(HotelkingException.class);
  }

}