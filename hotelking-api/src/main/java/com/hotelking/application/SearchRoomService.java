package com.hotelking.application;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.response.RoomPricesResponse;
import com.hotelking.dto.search.SearchRoomParameter;
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
    List<RoomNameWithPrice> stayNameAndPrices = fetchRoomPrices(
        searchRoomParameter,
        ReservationType.STAY,
        this::shouldIncludeStayPrice
    );

    List<RoomNameWithPrice> rentNameAndPrices = fetchRoomPrices(
        searchRoomParameter,
        ReservationType.RENT,
        this::shouldIncludeRentPrice
    );
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
      ReservationType reservationType,
      Predicate<SearchRoomParameter> includePriceCondition
  ) {
    if (includePriceCondition.test(searchRoomParameter)) {
      return searchQueryRepository.findRemainRooms(
              searchRoomParameter.hotelId(),
              searchRoomParameter.checkIn(),
              searchRoomParameter.checkOut(),
              reservationType)
          .stream()
          .map(room -> RoomNameWithPrice.from(
              room,
              reservationType,
              searchRoomParameter.checkIn(),
              searchRoomParameter.checkOut()
          ))
          .toList();
    }
    return new ArrayList<>();
  }

  private boolean shouldIncludeStayPrice(SearchRoomParameter searchRoomParameter) {
    return searchRoomParameter.reservationType() == null || searchRoomParameter.reservationType() == ReservationType.BOTH || searchRoomParameter.reservationType() == ReservationType.STAY;
  }

  private boolean shouldIncludeRentPrice(SearchRoomParameter searchRoomParameter) {
    return searchRoomParameter.reservationType() == null || searchRoomParameter.reservationType() == ReservationType.BOTH || searchRoomParameter.reservationType() == ReservationType.RENT;
  }
}
