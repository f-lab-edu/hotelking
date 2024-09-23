package com.hotelking.domain.booking;

import com.hotelking.domain.common.BaseTimeEntity;
import com.hotelking.domain.hotel.entity.RoomSchedule;
import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;
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
