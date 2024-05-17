package com.hotelking.domain.order;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.ReservationType;
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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROOM_ORDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "hotel_id", columnDefinition = "BIGINT", nullable = false)
  private long hotelId;

  @Column(name = "reservation_type", columnDefinition = "CHAR(10)", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private ReservationType reservationType;

  @Column(name = "check_in", columnDefinition = "TIMESTAMP(0)", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime checkIn;

  @Column(name = "check_out", columnDefinition = "TIMESTAMP(0)", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime checkOut;

  @Column(name = "user_id", columnDefinition = "BIGINT", nullable = false)
  private long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "room_type_id",
      foreignKey = @ForeignKey(name = "fk_order_to_room_type"),
      nullable = false,
      updatable = false
  )
  private RoomType roomType;

  @Enumerated(EnumType.STRING)
  @Column(name = "state", columnDefinition = "CHAR(10)", nullable = false)
  private OrderState state = OrderState.PENDING;
}
