package com.hotelking.domain.price;


import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.hotel.RoomType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomTypePrice extends BaseTimeEntity {

  @Id @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(
      name = "room_type_id",
      foreignKey = @ForeignKey(name = "fk_price_room_type_id"),
      updatable = false
  )
  private RoomType roomType;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "price_friday")),
      @AttributeOverride(name = "discount.discountAmount", column = @Column(name = "price_friday_discount_amount")),
      @AttributeOverride(name = "discount.discountRate", column = @Column(name = "price_friday_discount_rate"))
  })
  private PriceWithDiscount fridayPriceWithDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "price_saturday")),
      @AttributeOverride(name = "discount.discountAmount", column = @Column(name = "price_saturday_discount_amount")),
      @AttributeOverride(name = "discount.discountRate", column = @Column(name = "price_saturday_discount_rate"))
  })
  private PriceWithDiscount saturdayPriceWithDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "price_sunday")),
      @AttributeOverride(name = "discount.discountAmount", column = @Column(name = "price_sunday_discount_amount")),
      @AttributeOverride(name = "discount.discountRate", column = @Column(name = "price_sunday_discount_rate"))
  })
  private PriceWithDiscount sundayPriceWithDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "price_weekday")),
      @AttributeOverride(name = "discount.discountAmount", column = @Column(name = "price_weekday_discount_amount")),
      @AttributeOverride(name = "discount.discountRate", column = @Column(name = "price_weekday_discount_rate"))
  })
  private PriceWithDiscount weekdayPriceWithDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "base_price")),
      @AttributeOverride(name = "discount.discountAmount", column = @Column(name = "base_price_discount_amount")),
      @AttributeOverride(name = "discount.discountRate", column = @Column(name = "base_price_discount_rate"))
  })
  private PriceWithDiscount basePriceWithDiscount;

  @Embedded
  @AttributeOverride(name = "price.value", column = @Column(name = "extra_person_fee"))
  private Price additionalPersonFee;
}
