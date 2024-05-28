package com.hotelking.query;

import com.hotelking.domain.schedule.RoomSchedule;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomScheduleQuery extends JpaRepository<RoomSchedule, Long> {

  @Query("SELECT COUNT(DISTINCT rs.room.id) AS total_rooms " +
      "FROM RoomSchedule rs " +
      "JOIN rs.room r " +
      "JOIN r.type rt " +
      "WHERE rs.hotel.id = :hotelId " +
      "AND rt.id = :roomTypeId " +
      "AND rs.room.id IN (" +
      "    SELECT rs2.room.id " +
      "    FROM RoomSchedule rs2 " +
      "    WHERE rs2.hotel.id = :hotelId " +
      "    AND rs.reservationType = ReservationType.STAY" +
      "    AND rs2.isReserved = false " +
      "    AND (rs2.checkIn >= :startDate AND rs2.checkIn < :endDate) " +
      "    GROUP BY rs2.room.id " +
      "    HAVING COUNT(rs2.room.id) = :dayDiff" +
      ")")
  long countEmptyStayScheduleRooms(
      @Param("hotelId") Long hotelId,
      @Param("roomTypeId") Long roomTypeId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("dayDiff") long dayDiff
  );

  @Query(value = "SELECT COUNT(rs) " +
      "FROM RoomSchedule rs " +
      "INNER JOIN rs.room r " +
      "INNER JOIN r.type rt " +
      "WHERE rs.hotel.id = :hotelId " +
      "AND rt.id = :roomTypeId " +
      "AND rs.reservationType = 'DAESIL' " +
      "AND rs.checkIn = :checkIn " +
      "AND rs.isReserved = false")
  long countEmptyDasilSchduleRooms(
      @Param("hotelId") Long hotelId,
      @Param("roomTypeId") Long roomTypeId,
      @Param("checkIn") LocalDateTime checkIn
  );

}
