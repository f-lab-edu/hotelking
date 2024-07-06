package com.hotelking.dto.search.model;

import com.hotelking.domain.hotel.RoomType;
import java.util.List;
import lombok.Builder;

@Builder
public record RoomTypeSummary(
    long roomTypeId,
    String roomTypeName,
    String roomTypeContent,
    int roomTypeStandardPersons,
    int roomTypeMaxPersons,
    List<RoomTypeBed> roomTypeBeds,
    List<String> roomTypeImages,
    RoomTypeStayPriceSummary roomTypeStayPriceSummary,
    RoomTypeDaesilPriceSummary roomTypeDaesilPriceSummary
) {

  public static RoomTypeSummary from(
      RoomType roomType
  ) {
    return RoomTypeSummary.builder()
        .roomTypeId(roomType.getId())
        .roomTypeName(roomType.getName())
        .roomTypeContent(roomType.getContent())
        .roomTypeStayPriceSummary(null)
        .roomTypeDaesilPriceSummary(null)
        .build();
  }
}
