package com.hotelking.domain.price;

import static com.hotelking.exception.ErrorCode.PRICE_DISCOUNT_MIN;
import static com.hotelking.exception.ErrorCode.PRICE_DISCOUNT_NOT_NEGATIVE;
import static com.hotelking.exception.ErrorCode.PRICE_MIN;
import static com.hotelking.exception.ErrorCode.PRICE_TOTAL_MIN;

import com.hotelking.exception.HotelkingException;
import lombok.Builder;
import lombok.Data;

@Data
public class PriceAndDiscount {
    private static final long MIN_ROOM_PRICE = 10_000L;
    private static final long MIN_DISCOUNT_VALUE = 1_000L;
    private static final long MIN_RESULT_PRICE = 1_000L;

    private long price;
    private long discountAmount;
    private double discountPercent;

    @Builder
    public PriceAndDiscount(long price, long discountAmount) {
        checkMinimumPrice(price);
        checkDiscountRule(price, discountAmount);
        this.price = price;
        this.discountAmount = discountAmount;
        this.discountPercent = setDiscountPriceRate(price, discountAmount);
    }

    private void checkMinimumPrice(long price) {
        if (price < MIN_ROOM_PRICE) {
            throw new HotelkingException(PRICE_MIN, null);
        }
    }

    private double setDiscountPriceRate(long price, long discountPrice) {
      return (((double)discountPrice / (double)price) * 100);
    }

    private void checkDiscountRule(long price, long discountPrice) {
        if (discountPrice == 0) {
            return;
        }

        if (discountPrice < 0) {
            throw new HotelkingException(PRICE_DISCOUNT_NOT_NEGATIVE, null);
        }

        if (discountPrice <
            MIN_DISCOUNT_VALUE) {
            throw new HotelkingException(PRICE_DISCOUNT_MIN, null);
        }

        if ( Math.abs(price - discountPrice) < MIN_RESULT_PRICE) {
            throw new HotelkingException(PRICE_TOTAL_MIN, null);
        }
    }
}