package com.hotelking.dto.search.model;

import com.hotelking.domain.price.PriceAndDiscount;
import com.hotelking.domain.price.RoomPrice;
import com.hotelking.domain.price.RoomPriceType;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RoomPriceDto(
    long originPrice,
    long discountPrice
) {

  public static RoomPriceDto from(RoomPriceType roomPriceType, LocalDate start, LocalDate end) {
    PriceAndDiscount totalPrice = roomPriceType.getRoomPrice().getTotalPrice(start, end);
    return RoomPriceDto.builder()
        .originPrice(totalPrice.getPrice())
        .discountPrice(totalPrice.getDiscountAmount())
        .build();
  }

  public static RoomPriceDto from(RoomPrice roomPrice, LocalDate checkIn, LocalDate checkOut) {
    PriceAndDiscount totalPrice = roomPrice.getTotalPrice(checkIn, checkOut);
    return RoomPriceDto.builder()
        .originPrice(totalPrice.getPrice())
        .discountPrice(totalPrice.getDiscountAmount())
        .build();
  }
}
