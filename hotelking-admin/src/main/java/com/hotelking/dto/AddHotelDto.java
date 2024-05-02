package com.hotelking.dto;

import com.hotelking.domain.hotel.Hotel;

public record AddHotelDto(String name, String address, double lat, double lng) {

  public Hotel toEntity() {
    return Hotel.builder()
        .name(name)
        .address(address)
        .lat(lat)
        .lng(lng)
        .build();
  }
}