package com.hotelking.domain.hotel;

import com.hotelking.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomType extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", columnDefinition = "VARCHAR(45)", nullable = false)
  private String name;

  @Column(name = "desc", columnDefinition = "VARCHAR(255)", nullable = false)
  private String desc;

  @Column(name = "check_in_time", columnDefinition = "TIME(0)", nullable = false)
  @Temporal(value = TemporalType.TIME)
  private LocalTime checkInTime;

  @Column(name = "check_out_time", columnDefinition = "TIME(0)", nullable = false)
  @Temporal(value = TemporalType.TIME)
  private LocalTime checkOutTime;

  @Column(name = "hotel_id", columnDefinition = "BIGINT", nullable = false)
  private long hotelId;

}
