package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.dto.ReservationDto;
import roomescape.model.entity.Member;
import roomescape.model.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = List.of(
            Reservation.of(new Member("정준하"), LocalDate.now(), LocalTime.now()),
            Reservation.of(new Member("유재석"), LocalDate.now(), LocalTime.now()),
            Reservation.of(new Member("하하"), LocalDate.now(), LocalTime.now())
    );

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservationPage(Model model) {
        List<ReservationDto> dtos = reservations.stream().map(Reservation::toDto).toList();
        model.addAttribute("reservations", dtos);
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<ReservationDto> dtos = reservations.stream().map(Reservation::toDto).toList();
        return ResponseEntity.status(200).body(dtos);
    }
}
