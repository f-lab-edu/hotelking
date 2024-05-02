package com.hotelking.domain.hotel.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
      throw new IllegalArgumentException("위도는 33 ~ 43 사이어야합니다.");
    }

    if(MIN_LNG > lng || MAX_LNG < lng) {
      throw new IllegalArgumentException("경도는 124 ~ 132 사이어야합니다.");
    }
  }
}
