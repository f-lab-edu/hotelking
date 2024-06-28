package com.hotelking.domain.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

  @Column(name = "price")
  private int value;

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
}
