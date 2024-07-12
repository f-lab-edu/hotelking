package com.hotelking.dto.search.model;

import com.hotelking.query.projections.RoomWithPriceResult;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RoomNameWithPrice(
    long roomId,
    String roomName,
    RoomPriceDto roomPrice
) {
  public static RoomNameWithPrice from(
      RoomWithPriceResult roomWithPriceResult,
      LocalDate checkIn,
      LocalDate checkOut
  ) {
    return RoomNameWithPrice.builder()
        .roomId(roomWithPriceResult.roomId())
        .roomName(roomWithPriceResult.roomName())
        .roomPrice(RoomPriceDto.from(roomWithPriceResult.roomPrice(), checkIn, checkOut))
        .build();
  }
}
