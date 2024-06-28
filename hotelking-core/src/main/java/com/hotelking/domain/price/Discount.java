package com.hotelking.domain.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Discount {

  @Column(name = "discount_amount", insertable=false, updatable=false)
  private int discountAmount;

  @Column(name = "discount_rate", insertable=false, updatable=false)
  private int discountRate;
}
