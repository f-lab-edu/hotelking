package com.hotelking.global.util;

import static com.hotelking.exception.ErrorCode.USER_INVALID_PARAM_PHONE;

import com.hotelking.exception.HotelkingException;
import java.util.regex.Pattern;

public class PhoneValidator {

  private static final String PHONE_NUMBER_PATTERN = "^01[016789]-\\d{3,4}-\\d{4}$";
  private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

  public static void validate(String phoneNumber) {
    if (phoneNumber == null || !pattern.matcher(phoneNumber).matches()) {
      throw new HotelkingException(USER_INVALID_PARAM_PHONE, null);
    }
  }
}
