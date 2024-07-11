package com.hotelking.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PriceDateUtilTest {

  @Test
  void test() {
    // 두 날짜
    LocalDate start = LocalDate.parse("2024-06-01");
    LocalDate end = LocalDate.parse("2024-06-05");

    List<LocalDate> dates = start.datesUntil(end).toList();
    System.out.println(dates);

    System.out.println(Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays());
  }
}
