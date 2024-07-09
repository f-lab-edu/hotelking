package com.hotelking.domain.price;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.util.HashMap;
import lombok.Builder;
import lombok.Data;

@Data
public class CustomPrice {

    private PriceAndDiscount price;
    private HashMap<String, PriceAndDiscount> timePrice;

    @Builder
    public CustomPrice(
        PriceAndDiscount price,
        HashMap<String, PriceAndDiscount> timePrice
    ) {
        verifyNotNull(price);
        this.price = price;
        this.timePrice = timePrice;
    }

    private void verifyNotNull(PriceAndDiscount priceAndDiscount) {
        if (priceAndDiscount == null) {
            throw new HotelkingException(ErrorCode.PRICE_CUSTOM_DEFAULT_REQUIRED, null);
        }
    }
}