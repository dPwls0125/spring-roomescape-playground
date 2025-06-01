package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.dto.ReservationRequestDto;
import roomescape.model.dto.ReservationResponseDto;
import roomescape.model.entity.Member;
import roomescape.model.entity.Reservation;
import roomescape.repository.MemberRepository;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    public ReservationController(final MemberRepository memberRepository, final ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationResponseDto> dtos = reservationRepository.getAllReservations().stream().map(Reservation::toDto).toList();
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> saveReservation(@RequestBody ReservationRequestDto request) {

        Member member = new Member(request.getName());

        if (!memberRepository.isMemberExist(member)) {
            memberRepository.saveMember(member);
        }

        Reservation reservation = Reservation.builder()
                .member(member)
                .date(request.getDate())
                .time(request.getTime())
                .build();

        reservationRepository.saveReservation(reservation);
        ReservationResponseDto response = reservation.toDto();

        URI location = URI.create("/reservations/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{reservationId}") 
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }


}
