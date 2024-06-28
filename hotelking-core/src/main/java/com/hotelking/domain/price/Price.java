package com.hotelking.domain.price;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {

  public static final int MINIMUM_PRICE = 10000;

  @Column(name = "price")
  private int value;

  public Price(int value) {
    if (value < MINIMUM_PRICE) {
      throw new HotelkingException(ErrorCode.PRICE_MIN, null);
    }
    this.value = value;
  }
}
