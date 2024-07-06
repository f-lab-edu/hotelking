package com.hotelking.dto.search.model;

import com.hotelking.domain.price.PriceAndDiscount;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record RoomTypeStayPriceSummary(
    LocalTime checkInTime,
    LocalTime checkOutTime,
    long originalPrice,
    long discountPrice,
    long emptyRoomCount
) {

  public static RoomTypeStayPriceSummary from(
      LocalTime checkInTime,
      LocalTime checkOutime,
      PriceAndDiscount priceAndDiscount,
      long emptyRoomCount
  ) {
    return RoomTypeStayPriceSummary.builder()
        .checkInTime(checkInTime)
        .checkOutTime(checkOutime)
        .originalPrice(priceAndDiscount.getPrice())
        .discountPrice(priceAndDiscount.getPrice())
        .emptyRoomCount(emptyRoomCount)
        .build();
  }
}
