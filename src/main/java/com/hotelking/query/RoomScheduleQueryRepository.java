package com.hotelking.query;

import static com.hotelking.domain.hotel.entity.QRoom.room;
import static com.hotelking.domain.hotel.entity.QRoomSchedule.roomSchedule;
import static com.hotelking.domain.hotel.entity.QRoomType.roomType;

import com.hotelking.domain.hotel.dto.RoomAllocationType;
import com.hotelking.domain.hotel.dto.RoomDTO;
import com.hotelking.domain.hotel.entity.ReservationType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class RoomScheduleQueryRepository {

  private final JPAQueryFactory queryFactory;

  public RoomScheduleQueryRepository(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public Page<RoomDTO> searchRooms(
      List<Long> roomTypeIds,
      LocalDateTime checkIn,
      LocalDateTime checkOut,
      ReservationType reservationType,
      RoomAllocationType roomAllocationType,
      boolean reserved,
      Pageable pageable
  ) {
    long daysBetween = getDaysBetween(checkIn, checkOut);
    JPAQuery<RoomDTO> jpaQuery = queryFactory
        .select(Projections.fields(RoomDTO.class,
            room.id.as("roomId"),
            room.no.as("roomNo"),
            roomType.id.as("roomTypeId"),
            roomType.checkInTime.as("roomTypeCheckIn"),
            roomType.checkOutTime.as("roomTypeCheckOut"),
            roomType.name.as("roomTypeName")
        ))
        .from(roomSchedule)
        .join(room).on(room.eq(roomSchedule.room))
        .join(roomType).on(roomType.eq(room.type))
        .where(
            rangeBetween(checkIn, checkOut),
            eqReservationType(reservationType),
            roomSchedule.isReserved.eq(reserved),
            room.type.id.in(roomTypeIds)
        )
        .groupBy(room.id, roomType.id);

    if (reservationType == ReservationType.STAY && roomAllocationType.equals(RoomAllocationType.SINGLE)) {
        jpaQuery.having(roomSchedule.checkIn.countDistinct().eq(daysBetween + 1));
    }

    long totalCount = jpaQuery.fetchCount();
    List<RoomDTO> contents = jpaQuery
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return new PageImpl<>(contents, pageable, totalCount);
  }

  public Page<RoomDTO> searchMultiRooms(
      List<Long> roomTypeIds,
      LocalDateTime checkIn,
      LocalDateTime checkOut,
      ReservationType reservationType,
      boolean reserved,
      Pageable pageable
  ) {

    JPAQuery<RoomDTO> jpaQuery = queryFactory.select(Projections.fields(RoomDTO.class,
            room.id.as("roomId"),
            room.no.as("roomNo"),
            roomType.id.as("roomTypeId"),
            roomType.checkInTime.as("roomTypeCheckIn"),
            roomType.checkOutTime.as("roomTypeCheckOut"),
            roomType.name.as("roomTypeName"),
            roomSchedule.id.as("roomScheduleId"),
            roomSchedule.checkIn.as("roomScheduleCheckIn")
        ))
        .from(roomSchedule)
        .join(room).on(room.eq(roomSchedule.room)).fetchJoin()
        .join(roomType).on(roomType.eq(room.type)).fetchJoin()
        .where(
            rangeBetween(checkIn, checkOut),
            eqReservationType(reservationType),
            roomSchedule.isReserved.eq(reserved),
            room.type.id.in(roomTypeIds)
        );

    long totalCount = jpaQuery.fetchCount();
    List<RoomDTO> contents = jpaQuery
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return new PageImpl<>(contents, pageable, totalCount);
  }

  private BooleanExpression eqReservationType(ReservationType reservationType) {
    BooleanExpression bothType = roomSchedule.reservationType.eq(ReservationType.BOTH);

    if (reservationType.equals(ReservationType.DAESIL)) {
      return bothType.or(roomSchedule.reservationType.eq(ReservationType.DAESIL));
    }

    return bothType.or(roomSchedule.reservationType.eq(ReservationType.STAY));
  }

  private BooleanExpression rangeBetween(
      LocalDateTime checkIn,
      LocalDateTime checkOut
  ) {
    return roomSchedule.checkIn.between(checkIn, checkOut);
  }

  private long getDaysBetween(LocalDateTime checkIn, LocalDateTime checkOut) {
    return Duration.between(checkIn, checkOut).toDays();
  }
}
