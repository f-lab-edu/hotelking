package com.hotelking.domain.hotel.entity;

import com.hotelking.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomType extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", columnDefinition = "VARCHAR(45)", nullable = false)
  private String name;

  @Column(name = "content", columnDefinition = "VARCHAR(255)", nullable = false)
  private String content;

  @Column(name = "check_in_time", columnDefinition = "TIME(0)", nullable = false)
  @Temporal(value = TemporalType.TIME)
  private LocalTime checkInTime;

  @Column(name = "check_out_time", columnDefinition = "TIME(0)", nullable = false)
  @Temporal(value = TemporalType.TIME)
  private LocalTime checkOutTime;

  @Column(name = "hotel_id", columnDefinition = "BIGINT", nullable = false)
  private long hotelId;

  @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
  private List<Room> rooms;
}
