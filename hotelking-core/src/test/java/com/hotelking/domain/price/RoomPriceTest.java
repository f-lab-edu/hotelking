package com.hotelking.domain.price;

import static com.hotelking.utils.RoomPriceFactory.roomPriceWeekday;
import static org.assertj.core.api.Assertions.assertThat;

import com.hotelking.utils.RoomPriceFactory;
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
        .weekPrices(creteAllDay())
        .build();

    // then
    assertThat(roomPrice.getWeekdayPrices()).isEqualTo(creteAllDay());
  }

  @Test
  @DisplayName("투숙일에 대한 가격을 조회한다.")
  void test() {
    var date = LocalDate.parse("2024-07-01", formatter);
    var roomPrice = RoomPrice.builder()
        .weekPrices(creteAllDay())
        .datePrices(null)
        .build();
    var priceAndDiscount = roomPrice.getCurrentPriceAndDiscount(date, false);
    assertThat(priceAndDiscount.getPrice()).isEqualTo(100_000L);
  }

  @Test
  @DisplayName("조회 날짜가 datePrice 에 있는 경우 해당 가격으로 조회한다.")
  void test1() {
    var datePrice = RoomPriceFactory.datePrice(50_000L, 40_000L, "2024-07-05", "11:00:00");
    var priceWeekDays = createPriceWeekDay(100_000L, 0L, 80_000L);
    var roomPrice = RoomPrice.builder()
        .weekPrices(priceWeekDays)
        .datePrices(List.of(datePrice))
        .build();

    var date= LocalDate.parse("2024-07-05", formatter);
    var priceAndDiscount = roomPrice.getCurrentPriceAndDiscount(date, false);

    assertThat(priceAndDiscount.getPrice()).isEqualTo(50_000L);
    assertThat(priceAndDiscount.getDiscountAmount()).isEqualTo(40_000L);
    assertThat(priceAndDiscount.getDiscountPercent()).isEqualTo(80.0);
  }

  @Test
  @DisplayName("투숙일이 공휴일인 경우에는 공휴일 가격으로 조회한다.")
  void test2() {
    var priceWeekDays = createPriceWeekDay(100_000L, 0L, 80_000L);
    var date= LocalDate.parse("2024-07-01", formatter);
    var roomPrice = RoomPrice.builder()
        .weekPrices(priceWeekDays)
        .datePrices(List.of())
        .build();
    var priceAndDiscount = roomPrice.getCurrentPriceAndDiscount(date, true);

    assertThat(priceAndDiscount.getPrice()).isEqualTo(80_000L);
    assertThat(priceAndDiscount.getDiscountAmount()).isEqualTo(0L);
    assertThat(priceAndDiscount.getDiscountPercent()).isEqualTo(0L);
  }

  @Test
  @DisplayName("체크인과 체크아웃이 주어지면 평균 가격을 계산한다.")
  void avgPricePerOneDay() {
    var datePrice1 = RoomPriceFactory.datePrice(50_000L, 40_000L, "2024-07-01", "11:00:00");
    var datePrice2 = RoomPriceFactory.datePrice(60_000L, 40_000L, "2024-07-02", "11:00:00");
    var priceWeekDays = createPriceWeekDay(100_000L, 0L, 80_000L);

    var date1= LocalDate.parse("2024-07-01", formatter); // date price
    var date3= LocalDate.parse("2024-07-03", formatter); // weekday price -> custom price

    var roomPrice = RoomPrice.builder()
        .weekPrices(priceWeekDays)
        .datePrices(List.of(datePrice1, datePrice2))
        .build();

    PriceAndDiscount avgPrice = roomPrice.getTotalPrice(date1, date3);

    assertThat(avgPrice.getPrice()).isEqualTo(50_000L + 60_000L);
    assertThat(avgPrice.getDiscountAmount()).isEqualTo(40_000L + 40_000L);
  }

  @Test
  void priceDateTest() {
    var priceWeekDays = createPriceWeekDay(100_000L, 0L, 80_000L);
    var date= LocalDate.parse("2024-07-01", formatter);
    var roomPrice = RoomPrice.builder()
        .weekPrices(priceWeekDays)
        .datePrices(List.of())
        .build();

  }

  private List<WeekdayPrice> createPriceWeekDay(long price, long discountAmt, long customAmt) {
    return Arrays.stream(WeekType.values())
        .map(day -> roomPriceWeekday(
            day,
            price,
            discountAmt,
            customAmt)
        )
        .toList();
  }

  private List<WeekdayPrice> creteAllDay() {
    return Arrays.stream(WeekType.values())
        .map(day -> roomPriceWeekday(
            day,
            100_000L,
            0L,
            100_000L)
        )
        .toList();
  }
}