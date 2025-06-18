package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationResponseDto {
    private final long id;

    private final String name;

    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public ReservationResponseDto(final long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
