package com.hotelking.query;

import static com.hotelking.domain.hotel.QRoomType.roomType;
import static com.hotelking.domain.price.QRoomPriceType.roomPriceType;
import static com.hotelking.domain.room.QRoom.room;
import static com.hotelking.domain.schedule.QRoomSchedule.roomSchedule;
import static com.hotelking.domain.schedule.ReservationType.BOTH;
import static com.hotelking.domain.schedule.ReservationType.RENT;
import static com.hotelking.domain.schedule.ReservationType.STAY;
import static com.querydsl.jpa.JPAExpressions.select;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.query.condition.SearchRoomParameter;
import com.hotelking.query.projections.RoomWithPriceResult;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class SearchQueryRepository {

  private final JPAQueryFactory query;

  public SearchQueryRepository(JPAQueryFactory query) {
    this.query = query;
  }

  public List<RoomType> findRemainRooms(
      long hotelId,
      LocalDate startDate,
      LocalDate endDate,
      ReservationType reservationType
  ) {
    return query.selectFrom(roomType)
        .join(roomType.roomPrices, roomPriceType).fetchJoin()
        .where(
            roomType.id.in(
                select(roomType.id)
                    .from(roomSchedule)
                    .join(roomSchedule.room, room)
                    .join(room.type, roomType)
                    .where(
                        roomSchedule.hotel.id.eq(hotelId),
                        roomSchedule.checkIn.between(startDate, endDate.minusDays(1L)),
                        eqReservationType(reservationType),
                        roomSchedule.isReserved.isFalse())
                    .groupBy(room)
                    .having(roomSchedule.id.countDistinct().eq(getDaysBetween(startDate, endDate)))
            ))
        .fetch();
  }

  public List<RoomWithPriceResult> findRemainRoomNamesAndPrice(
      SearchRoomParameter searchRoomParameter
  ) {
    return query.select(Projections.constructor(
            RoomWithPriceResult.class,
            roomType.id.as("roomId"),
            roomType.name.as("roomName"),
            roomPriceType.roomPrice.as("roomPrice")
        ))
        .from(roomPriceType)
        .join(roomPriceType.roomType, roomType)
        .where(
            roomPriceType.type.eq(searchRoomParameter.type()),
            roomType.id.in(
                select(roomType.id)
                    .from(roomSchedule)
                    .join(roomSchedule.room, room)
                    .join(room.type, roomType)
                    .where(
                        roomSchedule.hotel.id.eq(searchRoomParameter.hotelId()),
                        roomSchedule.checkIn.between(
                            searchRoomParameter.checkIn(),
                            searchRoomParameter.checkOut().minusDays(1L)
                        ),
                        eqReservationType(searchRoomParameter.type()),
                        roomSchedule.isReserved.isFalse())
                    .groupBy(room)
                    .having(roomSchedule.id.countDistinct().eq(
                        getDaysBetween(searchRoomParameter.checkIn(), searchRoomParameter.checkOut()))
                    )
            ))
        .fetch();
  }

  private long getDaysBetween(LocalDate checkIn, LocalDate checkOut) {
    return Duration.between(checkIn.atStartOfDay(), checkOut.atStartOfDay()).toDays();
  }

  private BooleanExpression eqReservationType(ReservationType reservationType) {
    if (reservationType == STAY) {
      return roomSchedule.reservationType.in(STAY, BOTH);
    }

    return roomSchedule.reservationType.in(RENT, BOTH);
  }

}
