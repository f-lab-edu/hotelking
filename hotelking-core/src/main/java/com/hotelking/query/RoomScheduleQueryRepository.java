package com.hotelking.query;

import static com.hotelking.domain.hotel.QRoomType.roomType;
import static com.hotelking.domain.room.QRoom.room;
import static com.hotelking.domain.schedule.QRoomSchedule.roomSchedule;

import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.query.RoomAllocationType;
import com.hotelking.dto.query.RoomDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Duration;
import java.time.LocalDate;
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
      LocalDate checkIn,
      LocalDate checkOut,
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

  private BooleanExpression eqReservationType(ReservationType reservationType) {
    BooleanExpression bothType = roomSchedule.reservationType.eq(ReservationType.BOTH);

    if (reservationType.equals(ReservationType.RENT)) {
      return bothType.or(roomSchedule.reservationType.eq(ReservationType.RENT));
    }

    return bothType.or(roomSchedule.reservationType.eq(ReservationType.STAY));
  }

  private BooleanExpression rangeBetween(
      LocalDate checkIn,
      LocalDate checkOut
  ) {
    return roomSchedule.checkIn.between(checkIn, checkOut);
  }

  private long getDaysBetween(LocalDate checkIn, LocalDate checkOut) {
    return Duration.between(checkIn, checkOut).toDays();
  }
}
