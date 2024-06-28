package com.hotelking.domain.price;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceWithDiscount {

  @Embedded
  private Price price;

  @Embedded
  private Discount discount;

  public PriceWithDiscount(Price price, Discount discount) {
    this.price = price;
    this.discount = discount;
  }
}
