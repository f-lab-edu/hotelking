package com.hotelking.domain.price;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ROOM_TYPE_PRICE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomPriceType extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PRICE_TYPE_ID")
  private Long id;

  @ManyToOne
  @JoinColumn(
      name = "room_type_id",
      foreignKey = @ForeignKey(name = "fk_price_room_type_id"),
      nullable = false,
      updatable = false
  )
  private RoomType roomType;

  @Type(JsonType.class)
  @Column(name = "ROOM_STAY_PRICE", columnDefinition = "json")
  private RoomPrice roomPrice;

  @Column
  @Enumerated(EnumType.STRING)
  private ReservationType type;

  @Builder
  public RoomPriceType(
      long id,
      RoomPrice roomPrice,
      ReservationType type,
      RoomType roomType
  ) {
    verifyNotNull(roomType, roomPrice);
    this.id = id;
    this.roomType = roomType;
    this.roomPrice = roomPrice;
    this.type = type;
  }

  private void verifyNotNull(RoomType roomType, RoomPrice roomPrice) {
    if (roomType == null || roomPrice == null) {
      throw new HotelkingException(ErrorCode.PRICE_ROOM_TYPE_PRICE_NOT_NULL, null);
    }
  }
}
