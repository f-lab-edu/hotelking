package com.hotelking.domain.price;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import lombok.Builder;
import lombok.Data;

@Data
public class WeekdayPrice {

  // 컬럼 이름 길다.
  private WeekType day;
  private PriceAndDiscount price;
  private PriceAndDiscount customPrice;

  @Builder
  public WeekdayPrice(
      WeekType day,
      PriceAndDiscount price,
      PriceAndDiscount customPrice
  ) {
    verifyNotNullOrEmpty(day, price);
    this.day = day;
    this.price = price;
    this.customPrice = customPrice;
  }

  private void verifyNotNullOrEmpty(
      WeekType day,
      PriceAndDiscount defaultPrice
  ) {

    if (day == null) {
      throw new HotelkingException(ErrorCode.PRICE_DAY_NOT_NULL, null);
    }

    if (defaultPrice == null) {
      throw new HotelkingException(ErrorCode.PRICE_BASE_MONEY_REQUIRED, null);
    }
  }

  public PriceAndDiscount calPrice() {
    if (customPrice != null) {
      return customPrice;
    }
    return price;
  }
}