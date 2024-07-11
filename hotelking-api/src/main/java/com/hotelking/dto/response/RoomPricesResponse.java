package com.hotelking.dto.response;

import com.hotelking.dto.search.model.RoomNameWithPrice;
import com.hotelking.dto.search.model.RoomTypeInfoSummary;
import java.util.List;
import lombok.Builder;

@Builder
public record RoomPricesResponse(
    List<RoomTypeInfoSummary> rooms,
    List<RoomNameWithPrice> stayPrices,
    List<RoomNameWithPrice> rentPrices
) { }
