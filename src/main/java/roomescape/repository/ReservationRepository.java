package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.exception.BadRequestException;
import roomescape.model.entity.Reservation;

import java.util.Collections;
import java.util.List;

@Component
public class ReservationRepository {
    private final List<Reservation> reservations;

    public ReservationRepository(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public void saveReservation(final Reservation newReservation) {
        boolean isDuplicatedTime = reservations.stream()
                .anyMatch(reservation -> reservation.isHourDuplicated(newReservation.getTime()));

        if (isDuplicatedTime) throw new BadRequestException("이미 존재하는 예약과 시간이 중복됩니다.");
        if (reservations.contains(newReservation)) throw new BadRequestException("이미 존재하는 예약입니다.");

        reservations.add(newReservation);
    }

    public void deleteReservation(final Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);
        if (!removed) {
            throw new BadRequestException("존재하지 않는 예약 Id입니다.");
        }
    }

}
