package com.hotelking.dto.search.model;

import com.hotelking.domain.price.PriceAndDiscount;
import lombok.Builder;

@Builder
public record StayPrice(
    long originalPrice,
    long discountPrice,
    long leftRoomCount
) {
  public static StayPrice from(
      long remain,
      PriceAndDiscount priceAndDiscount
  ) {
    return StayPrice.builder()
        .leftRoomCount(remain)
        .originalPrice(priceAndDiscount.getPrice())
        .discountPrice(priceAndDiscount.getPrice())
        .build();
  }

  public static StayPrice from(PriceAndDiscount priceAndDiscount) {
    return StayPrice.builder()
        .originalPrice(priceAndDiscount.getPrice())
        .discountPrice(priceAndDiscount.getPrice())
        .build();
  }
}
