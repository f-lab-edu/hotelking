package com.hotelking.api.request;

import com.hotelking.api.common.RequestDtoCheckable;
import com.hotelking.api.dto.PhoneAuthDto;
import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;
import com.hotelking.global.util.PhoneNumberValidator;

public record PhoneAuthCodeRequest(
    String phoneNumber,
    AuthServiceType authServiceType
) implements RequestDtoCheckable {

  @Override
  public void validationCheck() {
    if (PhoneNumberValidator.isNotValidFormat(phoneNumber)) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_NUMBER, null);
    }
  }

  public PhoneAuthDto toDto() {
    return new PhoneAuthDto(phoneNumber);
  }
}
