package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.dto.ReservationResponseDto;
import roomescape.model.entity.Member;
import roomescape.model.entity.Reservation;
import roomescape.repository.MemberRepository;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(final MemberRepository memberRepository, final ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
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
                .member(getMember(name))
                .date(date)
                .time(time)
                .build();

        reservationRepository.saveReservation(reservation);
        ReservationResponseDto reservationResponseDto = reservation.toDto();

        return reservationResponseDto;
    }

    private Member getMember(String name) {
        Member member = new Member(name);

        if (!memberRepository.isMemberExist(member)) {
            memberRepository.saveMember(member);
        }

        return member;
    }

    public void deleteReservation(final Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }


}
