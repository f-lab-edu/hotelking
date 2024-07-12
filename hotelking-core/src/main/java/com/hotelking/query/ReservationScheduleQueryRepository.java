package com.hotelking.query;

import static com.hotelking.domain.schedule.QRoomSchedule.roomSchedule;

import com.hotelking.domain.schedule.QRoomSchedule;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.domain.schedule.RoomSchedule;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationScheduleQueryRepository {

  private final JPAQueryFactory queryFactory;

  public ReservationScheduleQueryRepository(JPAQueryFactory jpaQueryFactory) {
    this.queryFactory = jpaQueryFactory;
  }

  public List<RoomSchedule> findSchedulesByRoomIdAndDateRange(
      long roomId,
      LocalDate checkIn,
      LocalDate checkOut,
      ReservationType reservationType
  ) {
    QRoomSchedule roomSchedule2 = roomSchedule;
    return queryFactory.selectFrom(roomSchedule)
        .where(
            rangeBetween(checkIn, checkOut),
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
                    .having(roomSchedule2.checkIn.countDistinct().eq(getDaysBetween(checkIn.atStartOfDay(), checkOut.atStartOfDay()) + 1))
            )
        )
        .fetch();
  }

  private BooleanExpression eqReservationType(ReservationType reservationType, QRoomSchedule roomSchedule) {
    BooleanExpression bothType = roomSchedule.reservationType.eq(ReservationType.BOTH);
    if (reservationType.equals(ReservationType.RENT)) {
      return bothType.or(roomSchedule.reservationType.eq(ReservationType.RENT));
    }
    return bothType.or(roomSchedule.reservationType.eq(ReservationType.STAY));
  }

  private long getDaysBetween(LocalDateTime checkIn, LocalDateTime checkOut) {
    return Duration.between(checkIn, checkOut).toDays();
  }

  private BooleanExpression rangeBetween(LocalDate checkIn, LocalDate checkOut) {
    return roomSchedule.checkIn.between(checkIn, checkOut);
  }

}
