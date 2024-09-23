package com.hotelking.domain.booking;

import com.hotelking.domain.hotel.entity.ReservationType;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

  @Query(value = "select count(o) from RoomReservation o "
      + "where o.hotelId = :hotelId "
      + "and o.roomType.id = :roomTypeId "
      + "and o.checkIn >= :checkIn "
      + "and o.checkOut < :checkOut "
      + "and o.reservationType = :reservationType "
      + "and o.state <> 'CANCEL'")
  long countOccupiedRoomOrderWithType(
      long hotelId,
      long roomTypeId,
      LocalDateTime checkIn,
      LocalDateTime checkOut,
      ReservationType reservationType
  );
}
