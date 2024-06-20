package com.hotelking.domain.schedule;

import com.hotelking.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// join table
@Entity
@Table(name = "ROOM_SCHEDULE_TIME_SLOT")
public class RoomScheduleTimeSlot extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(
      name = "room_schedule_id",
      foreignKey = @ForeignKey(name = "fk_room_schedule_ts_to_room_schedule"),
      nullable = false
  )
  private RoomSchedule roomSchedule;

  @ManyToOne
  @JoinColumn(
      name = "time_slot_id",
      foreignKey = @ForeignKey(name = "fk_room_schedule_ts_to_time_slot"),
      nullable = false
  )
  private TimeSlot timeSlot;

  @Column(name = "is_reserved", nullable = false)
  private boolean isReserved;
}
