package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.dto.ReservationRequestDto;
import roomescape.model.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
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
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationResponseDto> response = reservationService.getAllReservations();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> saveReservation(@Valid @RequestBody ReservationRequestDto request) {
        ReservationResponseDto response = reservationService.saveReservation(request);
        URI location = URI.create("/reservations/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
