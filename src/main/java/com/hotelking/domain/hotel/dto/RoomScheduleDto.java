package com.hotelking.domain.hotel.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomScheduleDto {
  private Long roomScheduleId;
  private LocalDateTime roomScheduleCheckIn;
}
