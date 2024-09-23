package com.hotelking.query;

import com.hotelking.domain.hotel.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeQueryRepository extends JpaRepository<RoomType, Long> {

}
