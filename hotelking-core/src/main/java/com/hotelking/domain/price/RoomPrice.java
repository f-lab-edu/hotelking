package com.hotelking.domain.price;

import static com.hotelking.exception.ErrorCode.PRICE_WEEKS_OF_DAY_REQUIRED;

import com.hotelking.exception.HotelkingException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomPrice {

  private List<RoomPriceWeekday> weeksOfDay;

  @Builder
  public RoomPrice(List<RoomPriceWeekday> weeksOfDay) {
    verifyContainAllWeeksOfDay(weeksOfDay);
    this.weeksOfDay = weeksOfDay;
  }

  private void verifyContainAllWeeksOfDay(List<RoomPriceWeekday> weeksOfDay) {
    if (weeksOfDay == null || weeksOfDay.isEmpty()) {
      throw new HotelkingException(PRICE_WEEKS_OF_DAY_REQUIRED, null);
    }
  }

  private List<RoomPriceWeekType> getRoomPriceWeekTypes(List<RoomPriceWeekday> weeksOfDay) {
    return weeksOfDay.stream()
        .map(RoomPriceWeekday::getDay)
        .collect(Collectors.toList());
  }

  public PriceAndDiscount getCurrentPriceAndDiscount(LocalDate date, boolean isHoliday) {
    // 공휴일 요금 반환
    if (isHoliday) {
      RoomPriceWeekday roomPriceWeekday = weeksOfDay.stream()
          .filter(it -> it.getDay().name().equals(RoomPriceWeekType.HOLIDAY.name()))
          .findFirst()
          .get();

      return roomPriceWeekday.findDatePrice(date);
    }

    RoomPriceWeekday roomPriceWeekday = weeksOfDay.stream()
        .filter(it -> it.getDay().name().equals(date.getDayOfWeek().name()))
        .findFirst()
        .get();

    return roomPriceWeekday.findDatePrice(date);
  }
}
