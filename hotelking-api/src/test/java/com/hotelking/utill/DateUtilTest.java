package com.hotelking.utill;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;

class DateUtilTest {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Test
  void getDateRange() {

    LocalDate start = LocalDate.parse("2024-07-01", formatter);
    LocalDate end = LocalDate.parse("2024-07-07", formatter);

    List<LocalDate> dateRange = DateUtil.getDateRange(start, end);

    assertThat(dateRange.size()).isEqualTo(7);
    assertThat(dateRange).isEqualTo(
        List.of(
            LocalDate.parse("2024-07-01", formatter),
            LocalDate.parse("2024-07-02", formatter),
            LocalDate.parse("2024-07-03", formatter),
            LocalDate.parse("2024-07-04", formatter),
            LocalDate.parse("2024-07-05", formatter),
            LocalDate.parse("2024-07-06", formatter),
            LocalDate.parse("2024-07-07", formatter)
        )
    );
  }
}