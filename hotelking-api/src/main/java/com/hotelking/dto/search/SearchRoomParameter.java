package com.hotelking.dto.search;

import com.hotelking.domain.schedule.ReservationType;
import java.time.LocalDate;

public record SearchRoomParameter(
    long hotelId,
    LocalDate checkIn,
    LocalDate checkOut,
    ReservationType reservationType
){
}
