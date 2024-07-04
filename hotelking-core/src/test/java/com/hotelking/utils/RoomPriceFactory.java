package com.hotelking.utils;

import com.hotelking.domain.price.CustomPrice;
import com.hotelking.domain.price.PriceAndDiscount;
import com.hotelking.domain.price.RoomPrice;
import com.hotelking.domain.price.RoomPriceElement;
import com.hotelking.domain.price.RoomPriceWeekType;
import com.hotelking.domain.price.RoomPriceWeekday;
import java.util.HashMap;
import java.util.List;

public class RoomPriceFactory {

  public static RoomPrice roomPrice(
      RoomPriceWeekType day,
      long price,
      long discountAmount,
      String dateStr,
      String timeStr
  ) {
    return RoomPrice.builder()
        .weeksOfDay(List.of(roomPriceWeekday(day, price, discountAmount, dateStr, timeStr)))
        .build();
  }

  public static RoomPriceWeekday roomPriceWeekday(
      RoomPriceWeekType day,
      long price,
      long discountAmount,
      String dateStr,
      String timeStr
  ) {
    return RoomPriceWeekday.builder()
        .prices(List.of(roomPriceElement(price, discountAmount, dateStr, timeStr)))
        .day(day)
        .build();
  }

  public static RoomPriceElement roomPriceElement(
      long price,
      long discountAmount,
      String dateStr,
      String timeStr
  ) {
    return RoomPriceElement.builder()
        .defaultPrice(priceAndDiscount(price, discountAmount))
        .datePrices(List.of(datePrice(price, discountAmount, dateStr, timeStr)))
        .build();
  }

  public static HashMap<String, CustomPrice> datePrice(
      long price,
      long discountAmount,
      String dateStr,
      String timeStr
  ) {
    HashMap<String, CustomPrice> customPriceMap = new HashMap<>();
    customPriceMap.put(dateStr, customPrice(price, discountAmount, timeStr));
    return customPriceMap;
  }

  public static CustomPrice customPrice(
      long price,
      long discountAmount,
      String key
  ) {
    return CustomPrice.builder()
        .defaultPrice(priceAndDiscount(price, discountAmount))
        .timesPrice(priceAndDiscountKey(price, discountAmount, key))
        .build();
  }

  public static HashMap<String, PriceAndDiscount> priceAndDiscountKey(
      long price,
      long discountAmount,
      String key
  ) {
    HashMap<String, PriceAndDiscount> priceAndDiscountDate = new HashMap<>();
    priceAndDiscountDate.put(key, priceAndDiscount(price, discountAmount));
    return priceAndDiscountDate;
  }

  public static PriceAndDiscount priceAndDiscount(
      long price,
      long discountAmount
  ) {
    return PriceAndDiscount.builder()
        .price(price)
        .discountAmount(discountAmount)
        .build();
  }

  public static PriceAndDiscount defaultPriceAndDiscount() {
    return priceAndDiscount(100_000L, 0L);
  }
}
