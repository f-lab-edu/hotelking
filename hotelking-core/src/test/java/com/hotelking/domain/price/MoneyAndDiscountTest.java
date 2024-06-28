package com.hotelking.domain.price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MoneyAndDiscountTest {

  @Test
  @DisplayName("MoneyWithDiscount 객체 생성 - 성공")
  void createMoneyAndDiscount() {
    assertThatCode(() -> new MoneyAndDiscount(
        new Money(10000),
        new Money(1000)
    )).doesNotThrowAnyException();
  }

  @DisplayName("기본 룸 가격이 1만원 일 경우 객체 생성시 예외 발생 - 실패")
  @ParameterizedTest
  @ValueSource(ints = {0, 1, 9999})
  void throwExceptionMinRoomPrice(int price) {
    assertThatThrownBy(() -> new MoneyAndDiscount(
        new Money(price),
        new Money(1000)
    )).isInstanceOf(HotelkingException.class);
  }

  @DisplayName("할인 금액이 1000원 미만인 경우에는 객체 생성시 예외 발생. - 실패")
  @ParameterizedTest
  @ValueSource(ints = {0, 1, 999})
  void throwExceptionMinDiscount(int price) {
    assertThatThrownBy(() -> new MoneyAndDiscount(
        new Money(10000),
        new Money(price)
    )).isInstanceOf(HotelkingException.class);
  }

  @DisplayName("할인 금액을 적용한 룸 가격을 반환 - 성공")
  @ParameterizedTest
  @CsvSource(value = {
      "10000, 1000, 9000",
      "10000, 2000, 8000",
  })
  void getTotalRoomPrice(int price, int discountPrice, int totalPrice) {
    MoneyAndDiscount moneyAndDiscount = new MoneyAndDiscount(new Money(price), new Money(discountPrice));
    Money discountTotalPrice = moneyAndDiscount.getDiscountedPrice();
    assertThat(discountTotalPrice.getValue()).isEqualTo(totalPrice);
  }

  @DisplayName("할인 금액이 null 인 경우 총 룸 가격은 기본 가격과 동일 - 성공")
  @ParameterizedTest
  @ValueSource(ints = {10000, 100000, 1000000})
  void getTotalPriceWhenDiscountPriceIsNull(int price) {
    MoneyAndDiscount moneyAndDiscount = new MoneyAndDiscount(new Money(price), null);
    assertThat(moneyAndDiscount.getDiscountedPrice().getValue()).isEqualTo(price);
  }
}