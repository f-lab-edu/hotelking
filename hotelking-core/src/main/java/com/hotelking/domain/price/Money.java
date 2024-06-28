package com.hotelking.domain.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

  @Column(name = "price")
  private int value;

  @Builder
  public Money(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public Money minus(Money money) {
    if (money == null) {
      return this;
    }
    return new Money(Math.abs(this.value - money.value));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Money money)) {
      return false;
    }
    return value == money.value;
  }

  @Override
  public int hashCode() {
    return value;
  }
}
