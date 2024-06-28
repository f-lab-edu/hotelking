package com.hotelking.domain.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RoomPriceMoney {

  @Column(name = "price_amount", insertable=false, updatable=false)
  private int value;
}
