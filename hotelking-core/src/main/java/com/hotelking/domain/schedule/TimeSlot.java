package com.hotelking.domain.schedule;

import com.hotelking.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalTime;

@Entity
@Table(name = "TIME_SLOT")
public class TimeSlot extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "slot_time", columnDefinition = "TIME(0)", nullable = false)
  @Temporal(TemporalType.TIME)
  private LocalTime time;

  @Column(name = "slot_name", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private TimeSlotName timeSlotName;
}
