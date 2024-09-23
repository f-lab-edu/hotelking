package com.hotelking.api.dto.request;

import com.hotelking.dto.AddHotelDto;
import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class AddHotelRequest implements RequestDtoCheckable {

  @Schema(description = "호텔 이름", requiredMode = RequiredMode.REQUIRED)
  private String hotelName;

  @Schema(description = "호텔 주소", requiredMode = RequiredMode.REQUIRED)
  private String hotelAddress;

  @Schema(description = "호텔 위도", type = "double", requiredMode = RequiredMode.NOT_REQUIRED)
  private double hotelLat;

  @Schema(description = "호텔 경도", type = "double", requiredMode = RequiredMode.NOT_REQUIRED)
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
