package com.hotelking.dto.request;

import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.AddOrderDto;
import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderRequest implements RequestDtoCheckable {

  private Long hotelId;
  private Long roomTypeId;
  private LocalDateTime checkIn;
  private LocalDateTime checkOut;
  private ReservationType reservationType;

  @Override
  public void validationCheck() {
    checkInOutOrderCheck();
    daesilValidationCheck();
    stayValidationCheck();
  }

  private void checkInOutOrderCheck() {
    if (checkIn.isAfter(checkOut)) {
      throw new HotelkingException(ErrorCode.REQ_ORDER_DATE, null);
    }
  }

  private void daesilValidationCheck() {
    if (reservationType == ReservationType.DAESIL && !isSameDay(checkIn, checkOut)) {
      throw new HotelkingException(ErrorCode.REQ_ORDER_DAESIL, null);
    }
  }

  private void stayValidationCheck() {
    if (reservationType == ReservationType.STAY && isSameDay(checkIn, checkOut)) {
      throw new HotelkingException(ErrorCode.REQ_ORDER_STAY, null);
    }
  }

  private boolean isSameDay(LocalDateTime checkIn, LocalDateTime checkOut) {
    return checkIn.toLocalDate().isEqual(checkOut.toLocalDate());
  }

  public AddOrderDto toOrderDto() {
    return new AddOrderDto(hotelId, roomTypeId, checkIn, checkOut, reservationType);
  }
}
