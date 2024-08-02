package com.hotelking.application;

import com.hotelking.domain.order.Order;
import com.hotelking.domain.order.OrderRepository;
import com.hotelking.domain.reservation.RoomReservationRepository;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.AddRoomReservationDto;
import com.hotelking.dto.AppUser;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.query.RoomScheduleRepository;
import com.hotelking.query.RoomTypeQueryRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomReservationService {

  private final RoomReservationRepository roomReservationRepository;
  private final RoomScheduleRepository roomScheduleQuery;
  private final RoomTypeQueryRepository roomTypeQueryRepository;
  private final OrderRepository orderRepository;

  public RoomReservationService(RoomReservationRepository roomReservationRepository, RoomScheduleRepository roomScheduleQuery,
      RoomTypeQueryRepository roomTypeQueryRepository, OrderRepository orderRepository) {
    this.roomReservationRepository = roomReservationRepository;
    this.roomScheduleQuery = roomScheduleQuery;
    this.roomTypeQueryRepository = roomTypeQueryRepository;
    this.orderRepository = orderRepository;
  }

  public UUID addOrder(AppUser appUser, AddRoomReservationDto addRoomReservationDto) {
    checkRoomRevAvailability(addRoomReservationDto);
    Order savedOrder = orderRepository.save(addRoomReservationDto.toOrder(appUser));
    return savedOrder.getId();
  }

  public void checkRoomRevAvailability(AddRoomReservationDto addRoomReservationDto) {
    long emptyRoomScheduleCnt = countEmptyRoomSchedule(addRoomReservationDto);
    long orderedRoomCnt = countOrdered(addRoomReservationDto);
    checkAndThrowIfRoomOrderFull(emptyRoomScheduleCnt, orderedRoomCnt);
  }

  private void checkAndThrowIfRoomOrderFull(long emptyRoomCnt, long orderedRoomCnt) {
    if (emptyRoomCnt == orderedRoomCnt) {
      throw new HotelkingException(ErrorCode.ROOM_ORDER_FULL, log);
    }
  }

  private long countOrdered(AddRoomReservationDto addRoomReservationDto) {
    return roomReservationRepository.countOccupiedRoomOrderWithType(
        addRoomReservationDto.hotelId(),
        addRoomReservationDto.roomTypeId(),
        addRoomReservationDto.getStartOfDayOfCheckIn(),
        addRoomReservationDto.getLastOfDayOfCheckOut(),
        addRoomReservationDto.reservationType()
    );
  }

  private long countEmptyRoomSchedule(AddRoomReservationDto addRoomReservationDto) {
    long emptyScheduleRoomsCount = getEmptyScheduleRoomsCount(addRoomReservationDto);

    if (isScheduleFull(emptyScheduleRoomsCount)) {
      throw new HotelkingException(ErrorCode.ROOM_SCHEDULE_FULL, log);
    }

    return emptyScheduleRoomsCount;
  }

  private long getEmptyScheduleRoomsCount(AddRoomReservationDto addRoomReservationDto) {
    if (isDaeSilReservation(addRoomReservationDto)) {
      return countEmptyDaeSilScheduleRooms(addRoomReservationDto);
    } else {
      return countEmptyStayScheduleRooms(addRoomReservationDto);
    }
  }

  private boolean isDaeSilReservation(AddRoomReservationDto addRoomReservationDto) {
    return addRoomReservationDto.reservationType() == ReservationType.DAESIL;
  }

  private long countEmptyDaeSilScheduleRooms(AddRoomReservationDto addRoomReservationDto) {
    return roomScheduleQuery.countEmptyDasilScheduleRooms(
        addRoomReservationDto.hotelId(),
        addRoomReservationDto.roomTypeId(),
        addRoomReservationDto.checkIn().toLocalDate().atStartOfDay()
    );
  }

  private long countEmptyStayScheduleRooms(AddRoomReservationDto addRoomReservationDto) {
    return roomScheduleQuery.countEmptyStayScheduleRooms(
        addRoomReservationDto.hotelId(),
        addRoomReservationDto.roomTypeId(),
        addRoomReservationDto.checkIn(),
        addRoomReservationDto.checkOut(),
        addRoomReservationDto.getDayDiffer()
    );
  }

  private boolean isScheduleFull(long emptyScheduleRoomsCount) {
    return isEmpty(emptyScheduleRoomsCount);
  }

  private boolean isEmpty(long count) {
    return count == 0;
  }
}
