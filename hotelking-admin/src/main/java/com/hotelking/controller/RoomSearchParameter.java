package com.hotelking.controller;


import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.query.RoomAllocationType;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record RoomSearchParameter(
    RoomAllocationType allocationType,
    List<Long> types,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    ReservationType reservationType,
    boolean reserved
) {
}
