package com.hotelking.dto.search.model;

import java.util.List;

public record RoomTypeSummary(
    long roomTypeId,
    String roomTypeName,
    String roomTypeContent,
    int roomTypeStandardPersons,
    int roomTypeMaxPersons,
    List<RoomTypeBed> roomTypeBeds,
    int leftCount,
    RoomTypeImages roomTypeImages,
    RoomTypeStaySummary roomTypeStaySummary,
    RoomTypeDaesilSummary roomTypeDaesilSummary
) { }
