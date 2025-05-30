package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservationPage(Model model) {
        List<ReservationResponseDto> dtos = reservations.stream().map(Reservation::toDto).toList();
        model.addAttribute("reservations", dtos);
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationResponseDto> dtos = reservations.stream().map(Reservation::toDto).toList();
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> saveReservation(@RequestBody ReservationRequestDto request) {

        Member member = new Member(request.getName());

        if (!members.contains(member)) {
            members.add(member);
        }

        Reservation reservation = Reservation.builder()
                .member(member)
                .date(request.getDate())
                .time(request.getTime())
                .build();

        reservations.add(reservation);
        ReservationResponseDto response = reservation.toDto();

        URI location = URI.create("/reservations/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int reservationId) {
        Reservation reservation = reservations.stream().filter(r -> r.getId() == reservationId)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 ID입니다."));

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
