package com.hotelking.dto;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.order.Order;
import com.hotelking.domain.schedule.ReservationType;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record AddOrderDto(
    long hotelId,
    long roomTypeId,
    LocalDateTime checkIn,
    LocalDateTime checkOut,
    ReservationType reservationType
) {

  public long getDayDiffer() {
    return ChronoUnit.DAYS.between(checkIn, checkOut);
  }

  public LocalDateTime getMidNightTimeFromCheckin() {
   return checkIn.toLocalDate().atStartOfDay();
  }

  public Order toOrderWithType(ActiveUser user, RoomType roomType) {
    return Order.builder()
        .hotelId(hotelId)
        .roomType(roomType)
        .checkIn(checkIn)
        .checkOut(checkOut)
        .userId(user.id())
        .reservationType(reservationType)
        .build();
  }
}
