package com.hotelking.query.condition;

import static com.hotelking.exception.ErrorCode.SEARCH_CHECK_IN;
import static com.hotelking.exception.ErrorCode.SEARCH_HOTEL_ID;

import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.exception.HotelkingException;
import com.hotelking.util.DateValidator;
import java.time.LocalDate;

public record SearchRoomParameter(
    Long hotelId,
    LocalDate checkIn,
    LocalDate checkOut,
    ReservationType type
) {

  public SearchRoomParameter(
      Long hotelId,
      LocalDate checkIn,
      LocalDate checkOut,
      ReservationType type
  ) {
    if (hotelId == null || hotelId == 0) {
      throw new HotelkingException(SEARCH_HOTEL_ID, null);
    }

    if (checkIn == null) {
      throw new HotelkingException(SEARCH_CHECK_IN, null);
    }

    if (checkOut != null) {
      DateValidator.checkDateOrder(checkIn, checkOut);
    }

    this.hotelId = hotelId;
    this.checkIn = checkIn;
    this.checkOut = setCheckOut(checkIn, checkOut, type);
    this.type = type;
  }

  private LocalDate setCheckOut(
      LocalDate checkIn,
      LocalDate checkOut,
      ReservationType type
  ) {
    if (type == null || !type.isStay()) {
      return checkIn.plusDays(1L);
    }
    return checkOut;
  }

}
