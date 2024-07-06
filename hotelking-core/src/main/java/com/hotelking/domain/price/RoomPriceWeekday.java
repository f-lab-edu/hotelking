package com.hotelking.domain.price;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class RoomPriceWeekday {

  private RoomPriceWeekType day;
  private PriceAndDiscount defaultPrice;
  private PriceAndDiscount customDefaultPrice;
  private List<HashMap<String, CustomPrice>> customDatePrices;

  @Builder
  public RoomPriceWeekday(
      RoomPriceWeekType day,
      PriceAndDiscount defaultPrice,
      PriceAndDiscount customDefaultPrice,
      List<HashMap<String, CustomPrice>> customDatePrices
  ) {
    verifyNotNullOrEmpty(day, defaultPrice);
    this.day = day;
    this.defaultPrice = defaultPrice;
    this.customDefaultPrice = customDefaultPrice;
    this.customDatePrices = customDatePrices;
  }

  private void verifyNotNullOrEmpty(
      RoomPriceWeekType day,
      PriceAndDiscount defaultPrice
  ) {

    if (day == null) {
      throw new HotelkingException(ErrorCode.PRICE_DAY_NOT_NULL, null);
    }

    if (defaultPrice == null) {
      throw new HotelkingException(ErrorCode.PRICE_BASE_MONEY_REQUIRED, null);
    }
  }

  public PriceAndDiscount getCurrentPrice(LocalDate date, boolean isHoliday) {
    // custom default price


    // custom date 있는 지 확인


    //
    return null;
  }

  public long priceSum() {
    return 0;
  }

  public PriceAndDiscount findDatePrice(LocalDate date) {
    if (hasCustomDatePrice(date)) {
      return getDateCustomPrice(date);
    }

    if (customDefaultPrice != null) {
      return customDefaultPrice;
    }

    return defaultPrice;
  }

  private boolean isCustomDatePrice(LocalDate date) {
    if (customDatePrices == null) {
      return false;
    }
    return hasCustomDatePrice(date);
  }

  private boolean hasCustomDatePrice(LocalDate date) {
    return customDatePrices.stream().anyMatch(it -> it.containsKey(date.toString()));
  }

  private PriceAndDiscount getDateCustomPrice(LocalDate date) {
    HashMap<String, CustomPrice> customPriceWithDate = customDatePrices.stream()
        .filter(it -> it.containsKey(date.toString()))
        .findFirst()
        .orElse(null);

    CustomPrice customPrice = customPriceWithDate.get(date.toString());
    return customPrice.getDefaultPrice();
  }
}