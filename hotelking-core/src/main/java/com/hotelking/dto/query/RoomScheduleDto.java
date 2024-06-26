package com.hotelking.dto.query;

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
