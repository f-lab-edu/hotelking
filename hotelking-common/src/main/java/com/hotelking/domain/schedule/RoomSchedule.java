package com.hotelking.domain.schedule;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.hotel.Hotel;
import com.hotelking.domain.room.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ROOM_SCHEDULE")
public class RoomSchedule extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
                              columnDefinition = "BIGINT",
      nullable = false,
      updatable = false,
      foreignKey = @ForeignKey(name = "fk_room_schedule_to_room")
  )
  private Room room;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "hotel_id",
      columnDefinition = "BIGINT",
      nullable = false,
      updatable = false,
      foreignKey = @ForeignKey(name = "fk_room_schedule_to_hotel")
  )
  private Hotel hotel;

  @Column(name = "check_in", columnDefinition = "TIMESTAMP(0)", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime checkIn;

  @OneToMany(mappedBy = "roomSchedule", fetch = FetchType.EAGER)
  private List<RoomScheduleTimeSlot> roomScheduleTimeSlots;

  //   RoomScheduleTimeSlot 에서 하나라도 예약이 되있는 경우에 isReserved = True
  //   매번 List<RoomScheduleTimeSlot> -> any isReserved = true ? -> true
  @Column(name = "is_reserved", nullable = false)
  private boolean isReserved;

  @Enumerated(EnumType.STRING)
  @Column(name = "reservation_status", columnDefinition = "CHAR(10)", nullable = false)
  private ReservationType reservationType;
}
