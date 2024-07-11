package com.hotelking.query.model;

import com.hotelking.domain.schedule.ReservationType;
import java.util.List;

public record RoomTypeIds(
    ReservationType reservationType,
    List<Long> roomTypeIds
) {

  public static RoomTypeIds from(ReservationType type, List<Long> ids) {
    return new RoomTypeIds(type, ids);
  }
}
