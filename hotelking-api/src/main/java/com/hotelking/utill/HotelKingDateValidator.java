package com.hotelking.utill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HotelKingDateValidator {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static boolean isYYYYMMDDFormat(String date) {
    try {
      LocalDate.parse(date, DATE_FORMATTER);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean areCheckInAndCheckOutDatesValid(String checkIn, String checkOut) {
    return !stringToYYYYMMDD(checkIn).isAfter(stringToYYYYMMDD(checkOut));
  }

  public static LocalDate stringToYYYYMMDD(String strDate) {
    return LocalDate.parse(strDate);
  }

  public static LocalDateTime stringToDatetime(String strDate) {
    return stringToYYYYMMDD(strDate).atStartOfDay();
  }
}
