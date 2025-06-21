package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
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
        return reservationRepository.findAll()
                .stream()
                .map(Reservation::toDto)
                .toList();
    }

    public ReservationResponseDto saveReservation(final ReservationRequestDto requestDto) {
        long reservation_id = reservationRepository.save(requestDto);
        Reservation savedReservation = reservationRepository.findById(reservation_id);
        return savedReservation.toDto();
    }

    public void deleteReservation(final Long reservationId) {
        int result = reservationRepository.delete(reservationId);
        if (result == 0) {
            throw new BadRequestException("id값에 해당하는 예약이 존재하지 않습니다.");
        }
    }
}
