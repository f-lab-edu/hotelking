package com.hotelking.query;

import com.hotelking.domain.price.RoomPriceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypePriceRepository extends JpaRepository<RoomPriceType, Long> {

}
