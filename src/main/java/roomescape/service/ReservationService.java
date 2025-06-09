package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.dto.ReservationResponseDto;
import roomescape.model.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.getAllReservations()
                .stream()
                .map(Reservation::toDto)
                .toList();
    }

    public ReservationResponseDto saveReservation(final String name, final LocalDate date, final LocalTime time) {

        Reservation reservation = Reservation.builder()
                .id(reservationRepository.getNextId())
                .name(name)
                .date(date)
                .time(time)
                .build();

        reservationRepository.saveReservation(reservation);

        return reservation.toDto();
    }

    public void deleteReservation(final Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }
}
