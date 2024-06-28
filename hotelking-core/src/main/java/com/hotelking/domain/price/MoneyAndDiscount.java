package com.hotelking.domain.price;

import static com.hotelking.exception.ErrorCode.PRICE_DISCOUNT_MIN;
import static com.hotelking.exception.ErrorCode.PRICE_MIN;
import static com.hotelking.exception.ErrorCode.PRICE_NOT_NULL;
import static com.hotelking.exception.ErrorCode.PRICE_TOTAL_MIN;

import com.hotelking.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

  @Builder
  public MoneyAndDiscount(final Money price, final Money discountPrice) {
    checkPassPriceRule(price);
    if (discountPrice != null) {
      checkDiscountRule(price, discountPrice);
    }
    this.discountPrice = setDiscountPrice(discountPrice);
    this.discountPriceRate = setDiscountPriceRate(price, discountPrice);
    this.price = price;
  }

  private Money setDiscountPrice(Money discountedPrice) {
    if (discountedPrice == null) {
      return new Money(0);
    }
    return discountedPrice;
  }

  private void checkPassPriceRule(Money price) {
    if (price == null) {
      throw new HotelkingException(PRICE_NOT_NULL, null);
    }

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

  private int setDiscountPriceRate(Money price, Money discountPrice) {
    if (discountPrice == null) {
      return 0;
    }
    double discountRate = ((double) discountPrice.getValue() / price.getValue()) * 100;
    return (int) Math.round(discountRate);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MoneyAndDiscount that)) {
      return false;
    }

    if (discountPriceRate != that.discountPriceRate) {
      return false;
    }
    if (!Objects.equals(price, that.price)) {
      return false;
    }
    return Objects.equals(discountPrice, that.discountPrice);
  }

  @Override
  public int hashCode() {
    int result = price != null ? price.hashCode() : 0;
    result = 31 * result + (discountPrice != null ? discountPrice.hashCode() : 0);
    result = 31 * result + discountPriceRate;
    return result;
  }
}
