package com.hotelking.util;

import static com.hotelking.exception.ErrorCode.SEARCH_DATE_FORMAT;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static void checkDateOrder(LocalDate checkIn, LocalDate checkOut) {
    if (!checkOut.isAfter(checkIn)) {
      throw new HotelkingException(ErrorCode.SEARCH_DATE_ORDER, null);
    }
  }

  public static void checkDateFormat(String dateStr) {
    try {
      LocalDate.parse(dateStr, DATE_FORMATTER);
    } catch (DateTimeParseException e) {

    }
  }

  public static LocalDate toLocalDate(String dateStr) {
    try {
      return LocalDate.parse(dateStr, DATE_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new HotelkingException(SEARCH_DATE_FORMAT, null);
    }
  }
}
