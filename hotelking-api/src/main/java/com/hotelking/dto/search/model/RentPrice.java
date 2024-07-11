package com.hotelking.dto.search.model;

import com.hotelking.domain.price.PriceAndDiscount;
import lombok.Builder;

@Builder
public record RentPrice(
    long originalPrice,
    long discountPrice,
    long emptyRoomCount
) {

  public static RentPrice from(
      long emptyRoomCount,
      PriceAndDiscount priceAndDiscount
  ) {
    return RentPrice.builder()
        .emptyRoomCount(emptyRoomCount)
        .originalPrice(priceAndDiscount.getPrice())
        .discountPrice(priceAndDiscount.getDiscountAmount())
        .build();
  }

  public static RentPrice from(
      PriceAndDiscount priceAndDiscount
  ) {
    return RentPrice.builder()
        .originalPrice(priceAndDiscount.getPrice())
        .discountPrice(priceAndDiscount.getDiscountAmount())
        .build();
  }
}
