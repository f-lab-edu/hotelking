package com.hotelking.service;

import com.hotelking.domain.HotelRepository;
import com.hotelking.domain.hotel.Hotel;
import com.hotelking.dto.AddHotelDto;
import org.springframework.stereotype.Service;

@Service
public class AddHotelService {

  private final HotelRepository hotelRepository;

  public AddHotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  public long registerHotel(final AddHotelDto addHotel) {
    Hotel hotel = hotelRepository.save(addHotel.toEntity());
    return hotel.getId();
  }
}
