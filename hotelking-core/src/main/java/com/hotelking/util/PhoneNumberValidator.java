package com.hotelking.util;

import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class PhoneNumberValidator {

  private static final String PHONE_NUMBER_PATTERN = "^01[016789]-\\d{3,4}-\\d{4}$";
  private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

  public static boolean isNotValidFormat(String phoneNumber) {
    if (!StringUtils.hasLength(phoneNumber)) {
      return true;
    }
    return !pattern.matcher(phoneNumber).matches();
  }

}
