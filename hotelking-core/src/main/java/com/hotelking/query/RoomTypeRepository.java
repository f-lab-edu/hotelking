package com.hotelking.query;

import com.hotelking.domain.hotel.RoomType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

  @Query
  List<RoomType> findRoomTypesByHotelId(long hotelId);
}
