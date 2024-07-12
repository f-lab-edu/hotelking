package com.hotelking.application;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.response.RoomPricesResponse;
import com.hotelking.query.condition.SearchRoomParameter;
import com.hotelking.dto.search.model.RoomNameWithPrice;
import com.hotelking.dto.search.model.RoomTypeInfoSummary;
import com.hotelking.query.RoomTypeRepository;
import com.hotelking.query.SearchQueryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SearchRoomService {

  private final SearchQueryRepository searchQueryRepository;
  private final RoomTypeRepository roomTypeRepository;

  public SearchRoomService(SearchQueryRepository searchQueryRepository,
      RoomTypeRepository roomTypeRepository) {
    this.searchQueryRepository = searchQueryRepository;
    this.roomTypeRepository = roomTypeRepository;
  }

  @Transactional(readOnly = true)
  public RoomPricesResponse findRemainRooms(SearchRoomParameter searchRoomParameter) {
    List<RoomType> roomCategories = findAllRoomTypes(searchRoomParameter.hotelId());
    List<RoomNameWithPrice> stayNameAndPrices = fetchRoomPrices(searchRoomParameter, this::shouldIncludeStayPrice);
    List<RoomNameWithPrice> rentNameAndPrices = fetchRoomPrices(searchRoomParameter, this::shouldIncludeRentPrice);
    return RoomPricesResponse.builder()
        .rooms(roomCategories.stream()
            .map(RoomTypeInfoSummary::from)
            .toList())
        .stayPrices(stayNameAndPrices)
        .rentPrices(rentNameAndPrices)
        .build();
  }

  private List<RoomType> findAllRoomTypes(long hotelId) {
    return roomTypeRepository.findRoomTypesByHotelId(hotelId);
  }

  private List<RoomNameWithPrice> fetchRoomPrices(
      SearchRoomParameter searchRoomParameter,
      Predicate<SearchRoomParameter> includePriceCondition
  ) {
    if (includePriceCondition.test(searchRoomParameter)) {
      return searchQueryRepository.findRemainRoomNamesAndPrice(searchRoomParameter)
          .stream()
          .map(roomWithPriceResult -> RoomNameWithPrice.from(
              roomWithPriceResult,
              searchRoomParameter.checkIn(),
              searchRoomParameter.checkOut())
          )
          .toList();
    }
    return new ArrayList<>();
  }

  private boolean shouldIncludeStayPrice(SearchRoomParameter searchRoomParameter) {
    return searchRoomParameter.type() == null || searchRoomParameter.type() == ReservationType.BOTH || searchRoomParameter.type() == ReservationType.STAY;
  }

  private boolean shouldIncludeRentPrice(SearchRoomParameter searchRoomParameter) {
    return searchRoomParameter.type() == null || searchRoomParameter.type() == ReservationType.BOTH || searchRoomParameter.type() == ReservationType.RENT;
  }
}
