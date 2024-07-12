package com.hotelking.domain;

import static com.hotelking.utils.HotelFactory.hotel;
import static com.hotelking.utils.HotelFactory.roomType;

import com.hotelking.domain.hotel.HotelRepository;
import com.hotelking.query.RoomTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RepositoryTest {

  @Autowired
  protected HotelRepository hotelRepository;

  @Autowired
  protected RoomTypeRepository roomTypeRepository;

  @BeforeEach
  void saveAll() {
    hotelRepository.save(hotel(1L ,"TEST 호텔", "TEST 호텔 주소"));
    roomTypeRepository.save(roomType("더블 디럭스", 1L, "OTT 제공", "15:00:00", "11:00:00"));
  }
}
