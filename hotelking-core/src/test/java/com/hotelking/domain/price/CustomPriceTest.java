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
        .price(priceAndDiscount)
        .timePrice(timePriceMap)
        .build();

    assertThat(customPrice.getPrice()).isEqualTo(priceAndDiscount);
    assertThat(customPrice.getTimePrice()).isEqualTo(timePriceMap);
  }

  @Test
  @DisplayName("CustomPrice 생성 시 DefaultPrice 없으면 예외 발생 - 실패")
  void throwExceptionWhenDefaultPriceIsNull() {
    assertThatThrownBy(() -> CustomPrice.builder()
        .price(null)
        .timePrice(timePriceMap)
        .build())
        .isInstanceOf(HotelkingException.class);
  }

}