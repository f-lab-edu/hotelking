package com.hotelking.dto.response;

import com.hotelking.dto.query.RoomDTO;
import java.util.List;
import lombok.Builder;

@Builder
public record RoomResponse(
    long roomId,
    long roomNo,
    long roomTypeId,
    String roomTypeName
) {

  public static RoomResponse of(RoomDTO roomDTO) {
    return RoomResponse.builder()
        .roomId(roomDTO.getRoomId())
        .roomNo(roomDTO.getRoomNo())
        .roomTypeId(roomDTO.getRoomTypeId())
        .roomTypeName(roomDTO.getRoomTypeName())
        .build();
  }

  public static List<RoomResponse> listOf(List<RoomDTO> rooms) {
    return rooms.stream()
        .map(RoomResponse::of)
        .toList();

  }
}
