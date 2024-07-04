package com.hotelking.domain.price;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class RoomPriceWeekday {
    private RoomPriceWeekType day;
    private List<RoomPriceElement> prices;

    @Builder
    public RoomPriceWeekday(RoomPriceWeekType day, List<RoomPriceElement> prices) {
        verifyNotNullOrEmpty(day, prices);
        this.day = day;
        this.prices = prices;
    }

    private void verifyNotNullOrEmpty(RoomPriceWeekType day, List<RoomPriceElement> prices) {
        if (day == null) {
            throw new HotelkingException(ErrorCode.PRICE_DAY_NOT_NULL, null);
        }

        if (prices == null || prices.isEmpty()) {
            throw new HotelkingException(ErrorCode.PRICE_PRICES_REQUIRED, null);
        }
    }
}