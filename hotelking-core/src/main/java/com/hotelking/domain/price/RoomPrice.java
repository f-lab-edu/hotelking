package com.hotelking.domain.price;

import static com.hotelking.exception.ErrorCode.PRICE_ALL_DAY_REQUIRED;
import static com.hotelking.exception.ErrorCode.PRICE_WEEKS_OF_DAY_REQUIRED;

import com.hotelking.exception.HotelkingException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
public class RoomPrice {
    private List<RoomPriceWeekday> weeksOfDay;

    @Builder
    public RoomPrice(List<RoomPriceWeekday> weeksOfDay) {
        verifyContainAllWeeksOfDay(weeksOfDay);
        this.weeksOfDay = weeksOfDay;
    }

    private void verifyContainAllWeeksOfDay(List<RoomPriceWeekday> weeksOfDay) {
        if (weeksOfDay == null || weeksOfDay.isEmpty()) {
            throw new HotelkingException(PRICE_WEEKS_OF_DAY_REQUIRED, null);
        }

        // roomPriceWeekDay -> RoomPriceWeekType 이 mon, tue, ... sun 까지 모두 있는 지 확인하고 없으면 예외
        final List<RoomPriceWeekType> roomPriceWeekTypes = getRoomPriceWeekTypes(weeksOfDay);
        final Set<RoomPriceWeekType> allWeekTypes = EnumSet.allOf(RoomPriceWeekType.class);
        if (roomPriceWeekTypes.size() != weeksOfDay.size() || !new HashSet<>(roomPriceWeekTypes).containsAll(allWeekTypes)) {
            throw new HotelkingException(PRICE_ALL_DAY_REQUIRED, null);
        }
    }

    private List<RoomPriceWeekType> getRoomPriceWeekTypes(List<RoomPriceWeekday> weeksOfDay) {
        return weeksOfDay.stream()
            .map(RoomPriceWeekday::getDay)
            .collect(Collectors.toList());
    }
}
