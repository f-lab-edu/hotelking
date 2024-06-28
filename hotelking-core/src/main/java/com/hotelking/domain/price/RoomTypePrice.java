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
      @AttributeOverride(name = "discountPrice.value", column = @Column(name = "price_friday_discount_amount")),
      @AttributeOverride(name = "discountPriceRate", column = @Column(name = "price_friday_discount_rate"))
  })
  private MoneyAndDiscount fridayMoneyAndDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "price_saturday")),
      @AttributeOverride(name = "discountPrice.value", column = @Column(name = "price_saturday_discount_amount")),
      @AttributeOverride(name = "discountPriceRate", column = @Column(name = "price_saturday_discount_rate"))
  })
  private MoneyAndDiscount saturdayMoneyAndDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "price_sunday")),
      @AttributeOverride(name = "discountPrice.value", column = @Column(name = "price_sunday_discount_amount")),
      @AttributeOverride(name = "discountPriceRate", column = @Column(name = "price_sunday_discount_rate"))
  })
  private MoneyAndDiscount sundayMoneyAndDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "price_weekday")),
      @AttributeOverride(name = "discountPrice.value", column = @Column(name = "price_weekday_discount_amount")),
      @AttributeOverride(name = "discountPriceRate", column = @Column(name = "price_weekday_discount_rate"))
  })
  private MoneyAndDiscount weekdayMoneyAndDiscount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "price.value", column = @Column(name = "base_price")),
      @AttributeOverride(name = "discountPrice.value", column = @Column(name = "base_price_discount_amount")),
      @AttributeOverride(name = "discountPriceRate", column = @Column(name = "base_price_discount_rate"))
  })
  private MoneyAndDiscount baseMoneyAndDiscount;

  @Column(name = "extra_person_fee")
  private Money extraPersonFee;

  public RoomTypePrice(Long id, RoomType roomType, MoneyAndDiscount fridayMoneyAndDiscount,
      MoneyAndDiscount saturdayMoneyAndDiscount, MoneyAndDiscount sundayMoneyAndDiscount,
      MoneyAndDiscount weekdayMoneyAndDiscount, MoneyAndDiscount baseMoneyAndDiscount, Money extraPersonFee) {
    this.id = id;
    this.roomType = roomType;
    this.fridayMoneyAndDiscount = fridayMoneyAndDiscount;
    this.saturdayMoneyAndDiscount = saturdayMoneyAndDiscount;
    this.sundayMoneyAndDiscount = sundayMoneyAndDiscount;
    this.weekdayMoneyAndDiscount = weekdayMoneyAndDiscount;
    this.baseMoneyAndDiscount = baseMoneyAndDiscount;
    this.extraPersonFee = extraPersonFee;
  }
}
