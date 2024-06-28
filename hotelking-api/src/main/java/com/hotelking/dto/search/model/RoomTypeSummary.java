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
    List<String> roomTypeImages,
    RoomTypeStaySummary roomTypeStaySummary,
    RoomTypeDaesilSummary roomTypeDaesilSummary
) { }
