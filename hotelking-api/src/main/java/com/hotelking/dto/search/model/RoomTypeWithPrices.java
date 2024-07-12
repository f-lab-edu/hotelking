package com.hotelking.dto.search.model;

import java.util.List;

public record RoomTypeWithPrices(
    List<RoomTypeInfoSummary> rooms,
    List<RoomNameWithPrice> stayPrices,
    List<RoomNameWithPrice> rentPrices
) {
}
