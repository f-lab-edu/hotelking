package com.hotelking.api;


import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.query.RoomAllocationType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record RoomSearchParameter(
    RoomAllocationType allocationType,
    List<Long> types,
    LocalDateTime checkInDate,
    LocalDateTime checkOutDate,
    ReservationType reservationType,
    boolean reserved
) {
}
