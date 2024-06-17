package com.hotelking.dto.request;

public record AddReservationScheduleRequest(long reservationId, long roomId) {

  public AddReservationScheduleDto toDto() {
    return new AddReservationScheduleDto(reservationId, roomId);
  }
}
