package com.hotelking.utill;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtil {

  public static List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {
    return Stream.iterate(startDate, date -> date.plusDays(1))
        .limit(startDate.until(endDate).getDays() + 1)
        .collect(Collectors.toList());
  }
}
