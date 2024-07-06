package com.hotelking.domain.price;

import static com.hotelking.utils.RoomPriceFactory.roomPriceWeekday;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomPriceTest {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Test
  @DisplayName("Builder 로 RoomPrice 객체 생성 테스트")
  void createRoomPrice() {
    RoomPrice roomPrice = RoomPrice.builder()
        .weeksOfDay(creteAllDay())
        .build();

    // then
    assertThat(roomPrice.getWeeksOfDay()).isEqualTo(creteAllDay());
  }

  @Test
  @DisplayName("투숙일에 대한 가격을 조회한다.")
  void test() {
    var date= LocalDate.parse("2024-07-01", formatter);
    var roomPrice = RoomPrice.builder()
        .weeksOfDay(creteAllDay())
        .build();
    var priceAndDiscount = roomPrice.getCurrentPriceAndDiscount(date, false);

    assertThat(priceAndDiscount.getPrice()).isEqualTo(100_000L);
  }

  @Test
  @DisplayName("투숙일에 custom 가격이 있으면 custom 가격으로 조회한다.")
  void test1() {
    var priceWeekDays = createPriceWeekDay(100_000L, 0L, 80_000L, 70_000, "2024-07-05");
    var roomPrice = RoomPrice.builder()
        .weeksOfDay(priceWeekDays)
        .build();

    var date= LocalDate.parse("2024-07-05", formatter);
    var priceAndDiscount = roomPrice.getCurrentPriceAndDiscount(date, false);

    assertThat(priceAndDiscount.getPrice()).isEqualTo(70_000);
    assertThat(priceAndDiscount.getDiscountAmount()).isEqualTo(0L);
    assertThat(priceAndDiscount.getDiscountPercent()).isEqualTo(0L);
  }

  @Test
  @DisplayName("투숙일이 공휴일인 경우에는 공휴일 가격으로 조회한다.")
  void test2() {
    var priceWeekDays = createPriceWeekDay(100_000L, 0L, 80_000L, 70_000, "2024-07-05");
    var date= LocalDate.parse("2024-07-01", formatter);
    var roomPrice = RoomPrice.builder()
        .weeksOfDay(priceWeekDays)
        .build();
    var priceAndDiscount = roomPrice.getCurrentPriceAndDiscount(date, true);

    assertThat(priceAndDiscount.getPrice()).isEqualTo(80_000L);
    assertThat(priceAndDiscount.getDiscountAmount()).isEqualTo(0L);
    assertThat(priceAndDiscount.getDiscountPercent()).isEqualTo(0L);
  }

  private List<RoomPriceWeekday> createPriceWeekDay(long price, long discountAmt, long customAmt, long customDateAmt, String dateStr) {
    return Arrays.stream(RoomPriceWeekType.values())
        .map(day -> roomPriceWeekday(
            day,
            price,
            discountAmt,
            customAmt,
            customDateAmt,
            dateStr,
            "11:00:00")
        )
        .toList();
  }

  private List<RoomPriceWeekday> creteAllDay() {
    return Arrays.stream(RoomPriceWeekType.values())
        .map(day -> roomPriceWeekday(
            day,
            100_000L,
            0L,
            100_000L,
            100_000L,
            "2024-07-07",
            "11:00:00")
        )
        .toList();
  }
}