package com.hotelking.domain.hotel.vo;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {

  private static final int MIN_LAT = 33;
  private static final int MAX_LAT = 43;
  private static final int MIN_LNG = 124;
  private static final int MAX_LNG = 132;

  @Column(name = "lat", columnDefinition = "DECIMAL(9,6)", nullable = false)
  private double lat;

  @Column(name = "lng", columnDefinition = "DECIMAL(9,6)", nullable = false)
  private double lng;

  public Coordinate(double lat, double lng) {
    validateCoordinate(lat, lng);
    this.lat = lat;
    this.lng = lng;
  }

  private void validateCoordinate(double lat, double lng) {
    if (MIN_LAT > lat || MAX_LAT < lat) {
      throw new HotelkingException(ErrorCode.EXCEED_LAT, log);
    }

    if(MIN_LNG > lng || MAX_LNG < lng) {
      throw new HotelkingException(ErrorCode.EXCEED_LNG, log);
    }
  }
}
