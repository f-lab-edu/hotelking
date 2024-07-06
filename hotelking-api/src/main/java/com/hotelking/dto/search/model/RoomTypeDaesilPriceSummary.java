package com.hotelking.dto.search.model;

import com.hotelking.domain.price.PriceAndDiscount;
import lombok.Builder;

@Builder
public record RoomTypeDaesilPriceSummary(
    long hours,
    long originalPrice,
    long discountPrice,
    long leftRoomCount
) {
  public static RoomTypeDaesilPriceSummary from(
      long hours,
      PriceAndDiscount priceAndDiscount
  ) {
    return RoomTypeDaesilPriceSummary.builder()
        .hours(hours)
        .originalPrice(priceAndDiscount.getPrice())
        .discountPrice(priceAndDiscount.getPrice())
        .build();
  }
}
