package com.hotelking.domain.price;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.util.HashMap;
import lombok.Builder;
import lombok.Data;

@Data
public class CustomPrice {

    private PriceAndDiscount defaultPrice;
    private HashMap<String, PriceAndDiscount> timesPrice;

    @Builder
    public CustomPrice(
        PriceAndDiscount defaultPrice,
        HashMap<String, PriceAndDiscount> timesPrice
    ) {
        verifyNotNull(defaultPrice);
        this.defaultPrice = defaultPrice;
        this.timesPrice = timesPrice;
    }

    private void verifyNotNull(PriceAndDiscount priceAndDiscount) {
        if (priceAndDiscount == null) {
            throw new HotelkingException(ErrorCode.PRICE_CUSTOM_DEFAULT_REQUIRED, null);
        }
    }
}