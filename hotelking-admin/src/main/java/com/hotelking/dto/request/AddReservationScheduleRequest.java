package com.hotelking.dto.request;

import com.hotelking.dto.AddReservationScheduleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record AddReservationScheduleRequest(
    @Schema(description = "예약 번호", type = "number", minimum = "1") long reservationId,
    @Schema(description = "룸 번호", type = "number", minimum = "1") long roomId
) {

  public AddReservationScheduleDto toDto() {
    return new AddReservationScheduleDto(reservationId, roomId);
  }
}
