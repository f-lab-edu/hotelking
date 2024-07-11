package com.hotelking.dto.search.model;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.price.RoomPriceType;
import com.hotelking.domain.schedule.ReservationType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeSummary {

  private long roomTypeId;
  private String roomTypeName;
  private String roomTypeContent;
  private int roomTypeStandardPersons;
  private int roomTypeMaxPersons;
  private List<RoomTypeBed> roomTypeBeds;
  private List<String> roomTypeImages;
  private LocalTime roomCheckIn;
  private LocalTime roomCheckOut;
  private int roomRentHours;
  private RentPrice rentPrice;
  private StayPrice stayPrice;

  public static RoomTypeSummary from(
      RoomType roomType,
      ReservationType reservationType,
      LocalDate checkIn,
      LocalDate checkOut
  ) {
    RoomTypeSummaryBuilder summaryBuilder = RoomTypeSummary.builder()
        .roomTypeId(roomType.getId())
        .roomTypeName(roomType.getName())
        .roomTypeContent(roomType.getContent())
        .roomTypeStandardPersons(roomType.getMin())
        .roomTypeMaxPersons(roomType.getMax())
        .roomRentHours(roomType.getHours())
        .roomCheckIn(roomType.getCheckInTime())
        .roomCheckOut(roomType.getCheckOutTime());

    getRoomPriceType(roomType, reservationType)
        .map(roomPriceType -> roomPriceType.getRoomPrice().getTotalPrice(checkIn, checkOut))
        .ifPresent(totalPrice -> {
          if (reservationType == ReservationType.STAY) {
            summaryBuilder.stayPrice(StayPrice.from(totalPrice));
          } else if (reservationType == ReservationType.RENT) {
            summaryBuilder.rentPrice(RentPrice.from(totalPrice));
          }
        });

    return summaryBuilder.build();
  }

  private static Optional<RoomPriceType> getRoomPriceType(RoomType roomType, ReservationType reservationType) {
    return roomType.getRoomPrices().stream()
        .filter(it -> it.getType() == reservationType)
        .findFirst();
  }
}
