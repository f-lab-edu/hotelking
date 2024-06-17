package com.hotelking.query;

import static com.hotelking.domain.schedule.QRoomSchedule.roomSchedule;

import com.hotelking.domain.schedule.QRoomSchedule;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.domain.schedule.RoomSchedule;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduleQuery {

  private final JPAQueryFactory queryFactory;

  public ReservationScheduleQuery(JPAQueryFactory jpaQueryFactory) {
    this.queryFactory = jpaQueryFactory;
  }

  public boolean existedSchedule(
      long roomId,
      LocalDateTime checkIn,
      LocalDateTime checkOut,
      ReservationType reservationType
  ) {
    long daysBetween = getDaysBetween(checkIn, checkOut);
    Integer fetchFirst = queryFactory
        .selectOne()
        .from(roomSchedule)
        .where(
            roomSchedule.room.id.eq(roomId),
            roomSchedule.checkIn.between(checkIn, checkOut),
            eqReservationType(reservationType, roomSchedule),
            roomSchedule.isReserved.eq(false)
        )
        .groupBy(roomSchedule.room)
        .having(roomSchedule.checkIn.count().eq(daysBetween + 1))
        .fetchFirst();

    return fetchFirst != null;
  }

  private long getDaysBetween(LocalDateTime checkIn, LocalDateTime checkOut) {
    return Duration.between(checkIn, checkOut).toDays();
  }

  public List<RoomSchedule> findSchedulesByRoomIdAndDateRange(
      long roomId,
      LocalDateTime checkIn,
      LocalDateTime checkOut,
      ReservationType reservationType
  ) {
    QRoomSchedule roomSchedule2 = roomSchedule;
    System.out.println(roomId);
    System.out.println(checkIn);
    System.out.println(checkOut);
    System.out.println(reservationType);
    return queryFactory.selectFrom(roomSchedule)
        .where(
            roomSchedule.checkIn.between(checkIn, checkOut),
            eqReservationType(reservationType, roomSchedule),
            roomSchedule.isReserved.eq(false),
            roomSchedule.room.id.eq(roomId),
            roomSchedule.room.id.in (
                JPAExpressions.select(roomSchedule2.room.id)
                    .from(roomSchedule2)
                    .where(
                        roomSchedule2.room.id.eq(roomId),
                        roomSchedule2.checkIn.between(checkIn, checkOut),
                        eqReservationType(reservationType, roomSchedule2),
                        roomSchedule2.isReserved.eq(false)
                    )
                    .groupBy(roomSchedule2.room.id)
                    .having(roomSchedule2.checkIn.countDistinct().eq(getDaysBetween(checkIn, checkOut) + 1))
            )
        )
        .fetch();
  }

  private BooleanExpression eqReservationType(ReservationType reservationType, QRoomSchedule roomSchedule) {
    BooleanExpression bothType = roomSchedule.reservationType.eq(ReservationType.BOTH);

    if (reservationType.equals(ReservationType.DAESIL)) {
      return bothType.or(roomSchedule.reservationType.eq(ReservationType.DAESIL));
    }

    return bothType.or(roomSchedule.reservationType.eq(ReservationType.STAY));
  }


}
