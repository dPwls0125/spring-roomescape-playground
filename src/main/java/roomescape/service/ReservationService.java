package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.dto.ReservationRequestDto;
import roomescape.model.dto.ReservationResponseDto;
import roomescape.model.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAllReservations()
                .stream()
                .map(Reservation::toDto)
                .toList();
    }

    public long saveReservation(final ReservationRequestDto requestDto) {
        return reservationRepository.saveReservation(requestDto);
    }

    public void deleteReservation(final Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }
}
