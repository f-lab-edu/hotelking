package com.hotelking.dto.search.model;

import com.hotelking.domain.hotel.RoomType;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record RoomTypeInfoSummary(
    long roomTypeId,
    String roomTypeName,
    String roomTypeContent,
    int roomTypeStandardPersons,
    int roomTypeMaxPersons,
    LocalTime roomCheckIn,
    LocalTime roomCheckOut,
    int roomRentHours
) {

  public static RoomTypeInfoSummary from(RoomType roomType) {
    return RoomTypeInfoSummary.builder()
        .roomTypeId(roomType.getId())
        .roomTypeName(roomType.getName())
        .roomCheckIn(roomType.getCheckInTime())
        .roomCheckOut(roomType.getCheckOutTime())
        .roomTypeStandardPersons(roomType.getMin())
        .roomTypeMaxPersons(roomType.getMax())
        .roomRentHours(roomType.getHours())
        .build();
  }
}

