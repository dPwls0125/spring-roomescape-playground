package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDto {

    private String name;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public ReservationRequestDto(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
