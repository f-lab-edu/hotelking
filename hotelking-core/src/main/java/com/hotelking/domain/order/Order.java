package com.hotelking.domain.order;

import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.AppUser;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@ToString
@Getter
@RedisHash(value = "ORDER", timeToLive = 60 * 10)
public class Order implements Serializable {

  @Id
  private UUID id;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private Long hotelId;
  private Long roomTypeId;
  private AppUser appUser;
  private ReservationType reservationType;
  private LocalDateTime createdAt;

  @Builder
  public Order(
      UUID id,
      LocalDate checkIn,
      LocalDate checkOut,
      Long hotelId,
      Long roomTypeId,
      AppUser appUser,
      ReservationType reservationType
  ) {
    if (this.id == null) {
      this.id = id;
    }
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.hotelId = hotelId;
    this.roomTypeId = roomTypeId;
    this.appUser = appUser;
    this.reservationType = reservationType;
    this.createdAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Order order)) {
      return false;
    }

    return id.equals(order.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
