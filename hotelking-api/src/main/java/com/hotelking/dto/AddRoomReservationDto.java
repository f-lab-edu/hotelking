package com.hotelking.dto;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.order.Order;
import com.hotelking.domain.reservation.RoomReservation;
import com.hotelking.domain.schedule.ReservationType;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record AddRoomReservationDto(
    long hotelId,
    long roomTypeId,
    LocalDateTime checkIn,
    LocalDateTime checkOut,
    ReservationType reservationType
) {

  public long getDayDiffer() {
    return ChronoUnit.DAYS.between(checkIn, checkOut);
  }

  public Order toOrder(AppUser appUser) {
    return Order.builder()
        .hotelId(hotelId)
        .roomTypeId(roomTypeId)
        .checkIn(checkIn.toLocalDate())
        .checkOut(checkOut.toLocalDate())
        .appUser(appUser)
        .reservationType(reservationType)
        .build();
  }

  public RoomReservation toRoomRevWithType(AppUser user, RoomType roomType) {
    return RoomReservation.builder()
        .hotelId(hotelId)
        .roomType(roomType)
        .checkIn(checkIn)
        .checkOut(checkOut)
        .userId(user.userId())
        .reservationType(reservationType)
        .build();
  }

  public LocalDateTime getStartOfDayOfCheckIn() {
    return this.checkIn.toLocalDate().atStartOfDay();
  }

  public LocalDateTime getLastOfDayOfCheckOut() {
    return this.checkOut.toLocalDate().atStartOfDay().plusDays(1L);
  }
}
