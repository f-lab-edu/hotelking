package com.hotelking.domain.price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceAndDiscountTest {

  @ParameterizedTest
  @DisplayName("Builder 를 이용하여 PriceAndDiscount 생성 - 성공")
  @CsvSource({
      "10000, 9000, 90",
      "40000, 39000, 97.5"
  })
  void createPrice(long price, long discountAmount, double percent) {
    PriceAndDiscount priceAndDiscount = PriceAndDiscount.builder()
        .price(price)
        .discountAmount(discountAmount)
        .build();

    assertThat(priceAndDiscount.getPrice()).isEqualTo(price);
    assertThat(priceAndDiscount.getDiscountAmount()).isEqualTo(discountAmount);
    assertThat(priceAndDiscount.getDiscountPercent()).isEqualTo(percent);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 9999})
  @DisplayName("Price 최소 설정 금액 이하일 경우 예외 발생 - 실패")
  void throwExceptionUnderMinimum(long price) {
    assertThatThrownBy(() -> new PriceAndDiscount(price, 0))
        .isInstanceOf(HotelkingException.class);
  }


  @ParameterizedTest
  @ValueSource(ints = {-1, -2, -3, -9999})
  @DisplayName("Price 의 할인 금액이 음수 이면 예외 발생 - 실패")
  void throwExceptionWhenDiscountAmountIsNegative(long discountAmount) {
    assertThatThrownBy(() -> new PriceAndDiscount(10000, discountAmount))
        .isInstanceOf(HotelkingException.class);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 999})
  @DisplayName("Price 의 할인 금액이 최소 설정 금액 이하 일 경우 예외 발생 - 실패")
  void throwExceptionWhenDiscountAmountIsUnderMinAmount(long discountAmount) {
    assertThatThrownBy(() -> new PriceAndDiscount(10000, discountAmount))
        .isInstanceOf(HotelkingException.class);
  }

  @ParameterizedTest
  @CsvSource({
      "10000, 9001",
      "40000, 39001"
  })
  @DisplayName("할인을 적용한 최종 금액이 최소 설정 금액 보다 크지 아니면 예외발생 - 실패")
  void throwExceptionWhenDiscountAmountIsUnderMinAmount(long price, long discountAmount) {
    assertThatThrownBy(() -> new PriceAndDiscount(price, discountAmount))
        .isInstanceOf(HotelkingException.class);
  }

  @ParameterizedTest
  @CsvSource({
      "10000, 9000",
      "40000, 39000"
  })
  @DisplayName("할인을 적용한 최종 금액이 최소 설정 금액 보다 커야한다. - 실패")
  void checkTotalPriceRule(long price, long discountAmount) {
    assertThatCode(() -> new PriceAndDiscount(price, discountAmount))
        .doesNotThrowAnyException();
  }

}