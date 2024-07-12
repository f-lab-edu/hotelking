package com.hotelking.query.projections;

import com.hotelking.domain.price.RoomPrice;

public record RoomWithPriceResult(
    long roomId,
    String roomName,
    RoomPrice roomPrice
) {

}
