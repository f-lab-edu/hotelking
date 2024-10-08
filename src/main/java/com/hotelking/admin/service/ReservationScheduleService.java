package com.hotelking.admin.service;

import static com.hotelking.exception.ErrorCode.REV_ALREADY_SCHEDULED;
import static com.hotelking.exception.ErrorCode.REV_NOT_FOUND;
import static com.hotelking.exception.ErrorCode.REV_NO_SCHEDULE;

import com.hotelking.domain.reservation.RoomReservation;
import com.hotelking.domain.reservation.RoomReservationRepository;
import com.hotelking.domain.reservation_schedule.ReservationSchedule;
import com.hotelking.domain.reservation_schedule.ReservationScheduleRepository;
import com.hotelking.domain.schedule.RoomSchedule;
import com.hotelking.dto.AddReservationScheduleDto;
import com.hotelking.exception.HotelkingException;
import com.hotelking.query.ReservationScheduleQueryRepository;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class ReservationScheduleService {

  private final ReservationScheduleQueryRepository reservationScheduleQueryRepository;
  private final ReservationScheduleRepository reservationScheduleRepository;
  private final RoomReservationRepository roomReservationRepository;

  public ReservationScheduleService(
      ReservationScheduleQueryRepository reservationScheduleQueryRepository,
      ReservationScheduleRepository reservationScheduleRepository,
      RoomReservationRepository roomReservationRepository) {
    this.reservationScheduleQueryRepository = reservationScheduleQueryRepository;
    this.reservationScheduleRepository = reservationScheduleRepository;
    this.roomReservationRepository = roomReservationRepository;
  }



  public void registerReservationSchedule(AddReservationScheduleDto addReservationScheduleDto) {
    RoomReservation roomReservation = findSchedulableReservation(addReservationScheduleDto.reservationId());
    List<RoomSchedule> findEmptySchedules = findEmptySchedulesForReservation(addReservationScheduleDto, roomReservation);
    findEmptySchedules.forEach(RoomSchedule::markReservedTrue);
    saveReservationSchedules(findEmptySchedules, roomReservation);
  }

  private void saveReservationSchedules(List<RoomSchedule> findEmptySchedules, RoomReservation roomReservation) {
    findEmptySchedules.forEach(roomSchedule -> {
      ReservationSchedule reservationSchedule = new ReservationSchedule(roomReservation, roomSchedule);
      reservationScheduleRepository.save(reservationSchedule);
    });
  }

  private List<RoomSchedule> findEmptySchedulesForReservation(
      AddReservationScheduleDto addReservationScheduleDto,
      RoomReservation roomReservation
  ) {
    final List<RoomSchedule> findEmptySchedules = reservationScheduleQueryRepository.findSchedulesByRoomIdAndDateRange(
        addReservationScheduleDto.roomId(),
        roomReservation.getCheckIn(),
        roomReservation.getCheckOut(),
        roomReservation.getReservationType()
    );

    if (findEmptySchedules.isEmpty()) {
      throw new HotelkingException(REV_NO_SCHEDULE, null);
    }

    return findEmptySchedules;
  }

  private RoomReservation findSchedulableReservation(long reservationId) {
    RoomReservation roomReservation = findReservation(reservationId);
    if (!roomReservation.isSchedulable()) {
      throw new HotelkingException(REV_ALREADY_SCHEDULED, null);
    }
    return roomReservation;
  }

  private RoomReservation findReservation(long id) {
    return roomReservationRepository.findById(id)
        .orElseThrow(() -> new HotelkingException(REV_NOT_FOUND, null));
  }
}
