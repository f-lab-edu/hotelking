package com.hotelking.domain.price;

import static com.hotelking.utils.RoomPriceFactory.roomPriceWeekday;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelking.domain.RepositoryTest;
import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.query.RoomTypePriceRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RoomTypePriceRepositoryTest extends RepositoryTest {

  @Autowired
  private RoomTypePriceRepository roomTypePriceRepository;

  @Test
  @DisplayName("RoomTypePrice 저장 후 조회 테스트")
  void saveRoomTypePrice() throws JsonProcessingException {
    // given
    List<RoomPriceWeekday> roomPriceWeekdays = Arrays.stream(RoomPriceWeekType.values())
        .map(weekType -> roomPriceWeekday(weekType, 100000L, 0L, 100_100L, 100_100L, "2024-07-07", "11:00:00"))
        .toList();
    RoomPrice roomPrice = RoomPrice.builder()
        .weeksOfDay(roomPriceWeekdays)
        .build();

    RoomType roomType = roomTypeRepository.findById(1L).get();
    RoomPriceType priceType = RoomPriceType.builder()
        .id(1L)
        .roomType(roomType)
        .roomPrice(roomPrice)
        .type(ReservationType.STAY)
        .build();

    // when
    roomTypePriceRepository.saveAndFlush(priceType);
    RoomPriceType roomPriceType = roomTypePriceRepository.findById(1L).get();


    ObjectMapper objectMapper = new ObjectMapper();
    String jsonStr = objectMapper.writeValueAsString(roomPriceType.getRoomPrice());
    System.out.println(jsonStr);

//    // then
    assertThat(roomPriceType.getId()).isEqualTo(1L);
    assertThat(roomPriceType.getRoomPrice()).isEqualTo(roomPrice);
    assertThat(roomPriceType.getRoomType()).isEqualTo(roomType);
    assertThat(roomPriceType.getType()).isEqualTo(ReservationType.STAY);
  }
}
