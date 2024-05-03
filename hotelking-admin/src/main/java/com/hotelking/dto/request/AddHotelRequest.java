package com.hotelking.dto.request;

import com.hotelking.dto.AddHotelDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddHotelRequest {

  @NotBlank(message = "HOTEL_ADMIN_03")
  private String hotelName;

  @NotBlank(message = "HOTEL_ADMIN_04")
  private String hotelAddress;

  private double hotelLat;
  private double hotelLng;

  public AddHotelDto toAddHotel() {
    return new AddHotelDto(hotelName, hotelAddress, hotelLat, hotelLng);
  }
}
