package com.hotelking.dto.request;

import com.hotelking.dto.AddReservationScheduleDto;

public record AddReservationScheduleRequest(long reservationId, long roomId) {

  public AddReservationScheduleDto toDto() {
    return new AddReservationScheduleDto(reservationId, roomId);
  }
}
