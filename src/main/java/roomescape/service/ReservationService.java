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
        List<Reservation> rerservations = reservationRepository.findAll();

        boolean isDplicatedDateAndTime = rerservations.stream()
                .filter(reservation -> reservation.isDateDuplicated(requestDto.getDate()))
                .anyMatch(reservation -> reservation.isHourDuplicated(requestDto.getTime()));

        if (isDplicatedDateAndTime) {
            throw new BadRequestException("해당 날짜와 시간대의 예약이 이미 존재합니다.");
        }

        long id = reservationRepository.save(requestDto);
        return new ReservationResponseDto(id, requestDto.getName(), requestDto.getDate(), requestDto.getTime());
    }

    public void deleteReservation(final Long reservationId) {
        int result = reservationRepository.delete(reservationId);
        if (result == 0) {
            throw new BadRequestException("id값에 해당하는 예약이 존재하지 않습니다.");
        }
    }
}
