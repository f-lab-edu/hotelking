package com.hotelking.dto.request;

import com.hotelking.dto.auth.PhoneAuthDto;
import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.global.util.PhoneNumberValidator;

public class PhoneAuthCodeRequest implements RequestDtoCheckable {

  public String phoneNumber;

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
