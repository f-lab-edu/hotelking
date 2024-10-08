package com.hotelking.domain.hotel.entity;

import com.hotelking.domain.common.BaseTimeEntity;
import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity @Getter
@Table(name = "HOTEL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "address", columnDefinition = "VARCHAR(255)", nullable = false)
  private String address;

  @Embedded
  private Coordinate coordinate;

  @Builder
  public Hotel(long id, final String name, final String address, final double lat, final double lng) {
    this.id = id;

    if (name == null || name.isBlank()) {
      throw new HotelkingException(ErrorCode.REQ_HOTEL_NAME, log);
    }

    if (address == null || address.isBlank()){
      throw new HotelkingException(ErrorCode.REQ_HOTEL_ADDR, log);
    }

    this.name = name;
    this.address = address;
    this.coordinate = new Coordinate(lat, lng);
  }
}
