package com.hotelking.domain.hotel.entity;

import com.hotelking.domain.common.BaseTimeEntity;
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
import lombok.Getter;

@Entity
@Table(name = "ROOM_SCHEDULE")
@Getter
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

  @OneToMany(mappedBy = "roomSchedule", fetch = FetchType.LAZY)
  private List<RoomScheduleTimeSlot> roomScheduleTimeSlots;

  @Column(name = "is_reserved", nullable = false)
  private boolean isReserved;

  @Enumerated(EnumType.STRING)
  @Column(name = "reservation_status", columnDefinition = "CHAR(10)", nullable = false)
  private ReservationType reservationType;

  public void markReservedTrue() {
    this.isReserved = true;
  }
}
