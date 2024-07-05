package com.hotelking.utils;

import com.hotelking.domain.hotel.Hotel;
import com.hotelking.domain.hotel.RoomType;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HotelFactory {

  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  public static Hotel hotel(
      long id,
      String name,
      String address
  ) {
    return Hotel.builder()
        .id(id)
        .lng(127.123456)
        .lat(37.123214)
        .address(address)
        .name(name)
        .build();
  }

  public static RoomType roomType(
      String name,
      long hotelId,
      String content,
      String checkIn,
      String checkOut
  ) {
    return RoomType.builder()
        .name(name)
        .rooms(null)
        .checkInTime(LocalTime.parse(checkIn, TIME_FORMATTER))
        .checkOutTime(LocalTime.parse(checkOut, TIME_FORMATTER))
        .content(content)
        .hotelId(hotelId)
        .build();
  }

  public static RoomType roomType(
      long hotelId
  ) {
    return RoomType.builder()
        .name("HOTEL NAME")
        .rooms(null)
        .checkInTime(LocalTime.parse("11:00:00", TIME_FORMATTER))
        .checkOutTime(LocalTime.parse("15:00:00", TIME_FORMATTER))
        .content("HOTEL TYPE CONTENT")
        .hotelId(hotelId)
        .build();
  }
}
