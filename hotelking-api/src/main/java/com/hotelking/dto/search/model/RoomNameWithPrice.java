package com.hotelking.dto.search.model;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.ReservationType;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RoomNameWithPrice(
    long roomId,
    String roomName,
    RoomPrice roomPrice
) {

  public static RoomNameWithPrice from(
      RoomType roomType,
      ReservationType reservationType,
      LocalDate checkIn,
       LocalDate checkOut
  ) {
    return RoomNameWithPrice.builder()
        .roomId(roomType.getId())
        .roomName(roomType.getName())
        .roomPrice(
            RoomPrice.from(roomType.getTypePrice(reservationType),
                checkIn,
                checkOut))
        .build();
  }
}
