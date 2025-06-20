package roomescape.model.entity;

import lombok.Builder;
import roomescape.exception.BadRequestException;
import roomescape.model.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    @Builder
    private Reservation(final long id, final String name, final LocalDate date, final Time time) {
        validateArgumentNotEmpty(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponseDto toDto() {
        return new ReservationResponseDto(id, name, date, time.toDto());
    }

    public Time getTime() {
        return time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void validateArgumentNotEmpty(final String name, final LocalDate date, final Time time) {
        if (name.isBlank()) {
            throw new BadRequestException("이름 필드는 비어있을 수 없습니다.");
        }
        if (date == null || time == null) {
            throw new BadRequestException("날짜, 시간 필드는 비어있을 수 없습니다.");
        }
    }
}
