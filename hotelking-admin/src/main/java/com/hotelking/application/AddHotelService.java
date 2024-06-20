package com.hotelking.application;

import com.hotelking.repository.HotelRepository;
import com.hotelking.dto.AddHotelDto;
import org.springframework.stereotype.Service;

@Service
public class AddHotelService {

  private final HotelRepository hotelRepository;

  public AddHotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  public void registerHotel(final AddHotelDto addHotel) {
    hotelRepository.save(addHotel.toEntity());
  }
}
