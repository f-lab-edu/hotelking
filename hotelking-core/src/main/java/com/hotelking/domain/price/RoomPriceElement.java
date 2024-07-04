package com.hotelking.domain.price;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class RoomPriceElement {
    private PriceAndDiscount defaultPrice;
    private static final SimpleDateFormat customPriceKeyDateformat = new SimpleDateFormat("yyyy-MM-dd");

    // 2024-06-04
    @JsonDeserialize(using = CustomPriceDeserializer.class)
    private List<HashMap<String, CustomPrice>> datePrices;

    @Builder
    public RoomPriceElement(
        PriceAndDiscount defaultPrice,
        List<HashMap<String, CustomPrice>> datePrices
    ) {
        verifyNotNull(defaultPrice);
        this.defaultPrice = defaultPrice;
        this.datePrices = datePrices;
    }

    private void verifyNotNull(PriceAndDiscount defaultPrice) {
        if (defaultPrice == null) {
            throw new HotelkingException(ErrorCode.PRICE_MIN, null);
        }
    }
}
