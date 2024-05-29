package com.hotelking.global.util;

import java.util.regex.Pattern;

public class PhoneValidator {

  private static final String PHONE_NUMBER_PATTERN = "^01[016789]-\\d{3,4}-\\d{4}$";
  private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

  public static boolean isNotValidPhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      return true;
    }
    return !pattern.matcher(phoneNumber).matches();
  }
}
