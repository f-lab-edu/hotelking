package com.hotelking.domain.price;

import static com.hotelking.exception.ErrorCode.PRICE_DISCOUNT_MIN;
import static com.hotelking.exception.ErrorCode.PRICE_MIN;
import static com.hotelking.exception.ErrorCode.PRICE_TOTAL_MIN;

import com.hotelking.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoneyAndDiscount {

  private static final int MIN_RESULT_PRICE = 1000;
  private static final int MIN_ROOM_PRICE = 10000;
  private static final int MIN_DISCOUNT_VALUE = 1000;

  @Embedded
  private Money price;

  @Embedded
  private Money discountPrice;

  @Column(name = "discount_price_rate")
  private int discountPriceRate;

  @Transient
  private Money discountedPrice;

  public Money getDiscountedPrice() {
    return price.minus(discountPrice);
  }

  public MoneyAndDiscount(final Money price, final Money discountPrice) {
    checkRoomPriceRule(price);

    if (discountPrice != null) {
      checkDiscountRule(price, discountPrice);
    }

    this.price = price;
    this.discountPrice = discountPrice;
    this.discountPriceRate = getDiscountRate(price, discountPrice);
  }

  private void checkRoomPriceRule(Money price) {
    if (price.getValue() < MIN_ROOM_PRICE) {
      throw new HotelkingException(PRICE_MIN, null);
    }
  }

  private void checkDiscountRule(Money price, Money discountPrice) {
    if (discountPrice.getValue() < MIN_DISCOUNT_VALUE) {
      throw new HotelkingException(PRICE_DISCOUNT_MIN, null);
    }

    if ( price.minus(discountPrice).getValue() < MIN_RESULT_PRICE) {
      throw new HotelkingException(PRICE_TOTAL_MIN, null);
    }
  }

  private int getDiscountRate(Money price, Money discountPrice) {
    if (discountPrice == null) {
      return 0;
    }
    double discountRate = ((double) discountPrice.getValue() / price.getValue()) * 100;
    return (int) Math.round(discountRate);
  }
}
