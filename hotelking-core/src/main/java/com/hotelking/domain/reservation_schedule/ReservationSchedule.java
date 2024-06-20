package com.hotelking.domain.reservation_schedule;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.reservation.RoomReservation;
import com.hotelking.domain.schedule.RoomSchedule;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSchedule extends BaseTimeEntity {

  @EmbeddedId
  private ReservationScheduleId reservationScheduleId;

  public ReservationSchedule(RoomReservation roomReservation, RoomSchedule roomSchedule) {
    if (roomReservation.isSchedulable()) {
      throw new HotelkingException(ErrorCode.REV_ALREADY_SCHEDULED, null);
    }
    this.reservationScheduleId = new ReservationScheduleId(roomReservation, roomSchedule);
  }

}
