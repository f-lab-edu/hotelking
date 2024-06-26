package com.hotelking.dto.query;

import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RoomDTO{
  private Long roomId;
  private Integer roomNo;
  private Long roomTypeId;
  private LocalTime roomTypeCheckIn;
  private LocalTime roomTypeCheckOut;
  private String roomTypeName;
  private List<RoomScheduleDto> roomScheduleDtos;
}
