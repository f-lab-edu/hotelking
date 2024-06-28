package com.hotelking.domain.price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.hotelking.domain.hotel.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomTypePriceTest {

  @Test
  @DisplayName("Builder 를 이용하여 RoomTypePrice 객체 생성 - 성공")
  void createRoomTypePrice() {
    assertThatCode(() -> RoomTypePrice.builder()
        .roomType(RoomType.builder().build())
        .baseMoneyAndDiscount(
            MoneyAndDiscount.builder()
                .price(new Money(100000))
                .discountPrice(new Money(1000))
                .build()
        ).build()
    ).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Builder 로 객체 생성 시 기준 가격만 있을 경우 주중,금,토,일 가격을 모두 기준가격으로 설정 - 성공")
  void basePriceIsSameWithOtherPriceWhenNoGivenOtherPrice() {
    MoneyAndDiscount baseMoneyAndDiscount = MoneyAndDiscount.builder()
        .price(new Money(100000))
        .discountPrice(new Money(1000))
        .build();
    RoomTypePrice roomTypePrice = RoomTypePrice.builder()
        .roomType(RoomType.builder().build())
        .baseMoneyAndDiscount(
            MoneyAndDiscount.builder()
                .price(new Money(100000))
                .discountPrice(new Money(1000))
                .build())
        .build();

    assertThat(roomTypePrice.getFridayMoneyAndDiscount()).isNotNull();
    assertThat(roomTypePrice.getSaturdayMoneyAndDiscount()).isNotNull();
    assertThat(roomTypePrice.getSundayMoneyAndDiscount()).isNotNull();
    assertThat(roomTypePrice.getWeekdayMoneyAndDiscount()).isNotNull();

    assertThat(roomTypePrice.getFridayMoneyAndDiscount()).isEqualTo(baseMoneyAndDiscount);
    assertThat(roomTypePrice.getSaturdayMoneyAndDiscount()).isEqualTo(baseMoneyAndDiscount);
    assertThat(roomTypePrice.getSundayMoneyAndDiscount()).isEqualTo(baseMoneyAndDiscount);
    assertThat(roomTypePrice.getWeekdayMoneyAndDiscount()).isEqualTo(baseMoneyAndDiscount);
  }
}