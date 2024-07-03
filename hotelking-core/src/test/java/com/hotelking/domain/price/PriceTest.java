package com.hotelking.domain.price;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelking.exception.HotelkingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoneyTest {

  @Test
  @DisplayName("Price 가격 생성 - 성공")
  void createPrice() {
    assertThatCode(() -> new Money(10000))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 9999})
  @DisplayName("Price 최소 설정 금액 이하일 경우 예외 발생 - 실패")
  void throwExceptionUnderMinimum(int price) {
    assertThatThrownBy(() -> new Money(price))
        .isInstanceOf(HotelkingException.class);
  }
}