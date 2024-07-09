package com.hotelking.domain.price;

import static com.hotelking.exception.ErrorCode.PRICE_WEEKS_OF_DAY_REQUIRED;

import com.hotelking.exception.HotelkingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomPrice {

  private List<WeekdayPrice> weekdayPrices;
  private List<HashMap<String, CustomPrice>> datePrices;

  @Builder
  public RoomPrice(
      List<WeekdayPrice> weekPrices,
      List<HashMap<String, CustomPrice>> datePrices
  ) {
    verifyContainAllWeeksOfDay(weekPrices);
    this.weekdayPrices = weekPrices;
    this.datePrices = datePrices;
  }

  private void verifyContainAllWeeksOfDay(List<WeekdayPrice> weeksOfDay) {
    if (weeksOfDay == null || weeksOfDay.isEmpty()) {
      throw new HotelkingException(PRICE_WEEKS_OF_DAY_REQUIRED, null);
    }
  }

  public PriceAndDiscount getCurrentPriceAndDiscount(LocalDate date, boolean isHoliday) {
    // get date price
    if (datePrices != null) {
      HashMap<String, CustomPrice> customDatePrice = datePrices.stream()
          .filter(datePrice -> datePrice.containsKey(date.toString()))
          .findFirst()
          .orElse(null);

      if (customDatePrice != null) {
        CustomPrice customPrice = customDatePrice.get(date.toString());
        return customPrice.getPrice();
      }
    }

    // 공휴일 요금 반환
    if (isHoliday) {
      WeekdayPrice weekdayPrice = weekdayPrices.stream()
          .filter(it -> it.getDay().name().equals(WeekType.HOLIDAY.name()))
          .findFirst()
          .get();

      return weekdayPrice.calPrice();
    }

    WeekdayPrice weekdayPrice = weekdayPrices.stream()
        .filter(it -> it.getDay().name().equals(date.getDayOfWeek().name()))
        .findFirst()
        .get();
    return weekdayPrice.calPrice();
  }

  public PriceAndDiscount getTotalPrice(List<LocalDate> dates) {
    long[] sums = dates.stream()
        .map(date -> getCurrentPriceAndDiscount(date, false))
        .collect(() -> new long[2],
            (a, pad) -> {
              a[0] += pad.getPrice();
              a[1] += pad.getDiscountAmount();
            },
            (a1, a2) -> {
              a1[0] += a2[0];
              a1[1] += a2[1];
            });
    return new PriceAndDiscount(sums[0], sums[1]);
  }
}
