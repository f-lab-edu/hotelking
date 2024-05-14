package com.hotelking.dto.request;

import com.hotelking.dto.AddHotelDto;
import com.hotelking.dto.Checkable;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class AddHotelRequest implements Checkable {

  private String hotelName;
  private String hotelAddress;
  private double hotelLat;
  private double hotelLng;

  public AddHotelDto toAddHotel() {
    return new AddHotelDto(hotelName, hotelAddress, hotelLat, hotelLng);
  }

  @Override
  public void validationCheck() {
    if (hotelName == null || hotelName.isBlank()) {
      throw new HotelkingException(ErrorCode.REQ_HOTEL_NAME, log);
    }

    if (hotelAddress == null || hotelAddress.isBlank()) {
      throw new HotelkingException(ErrorCode.REQ_HOTEL_ADDR, log);
    }
  }
}
