package com.hotelking.domain.booking;

import com.hotelking.domain.hotel.entity.RoomSchedule;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationScheduleId implements Serializable {

  @ManyToOne
  private RoomReservation roomReservation;

  @ManyToOne
  private RoomSchedule roomSchedule;

  public ReservationScheduleId(RoomReservation roomReservation, RoomSchedule roomSchedule) {
    this.roomReservation = roomReservation;
    this.roomSchedule = roomSchedule;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ReservationScheduleId that = (ReservationScheduleId) o;

    if (!roomReservation.equals(that.roomReservation)) return false;
    return roomSchedule.equals(that.roomSchedule);
  }

  @Override
  public int hashCode() {
    int result = roomReservation.hashCode();
    result = 31 * result + roomSchedule.hashCode();
    return result;
  }
}
