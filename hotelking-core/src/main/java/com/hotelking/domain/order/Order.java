package com.hotelking.domain.order;

import com.hotelking.dto.AppUser;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
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
  private Integer amount;
  private AppUser appUser;

  public Order(
      UUID id,
      LocalDate checkIn,
      LocalDate checkOut,
      Long hotelId,
      Long roomTypeId,
      Integer amount,
      AppUser appUser
  ) {
    this.id = id;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.hotelId = hotelId;
    this.roomTypeId = roomTypeId;
    this.amount = amount;
    this.appUser = appUser;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Order order)) {
      return false;
    }

    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
