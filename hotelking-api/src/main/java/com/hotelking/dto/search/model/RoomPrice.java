package com.hotelking.dto.search.model;

import com.hotelking.domain.price.PriceAndDiscount;
import com.hotelking.domain.price.RoomPriceType;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RoomPrice(
    long originPrice,
    long discountPrice
) {

  public static RoomPrice from(RoomPriceType roomPriceType, LocalDate start, LocalDate end) {
    PriceAndDiscount totalPrice = roomPriceType.getRoomPrice().getTotalPrice(start, end);
    return RoomPrice.builder()
        .originPrice(totalPrice.getPrice())
        .discountPrice(totalPrice.getDiscountAmount())
        .build();
  }
}
