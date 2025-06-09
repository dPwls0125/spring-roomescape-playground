package roomescape.model.entity;

import roomescape.exception.BadRequestException;
import roomescape.model.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reservation {
    private final long id;
    private final Member member;
    private final LocalDate date;
    private final LocalTime time;

    private Reservation(final long id, final Member member, final LocalDate date, final LocalTime time) {
        if (member == null || date == null || time == null) {
            throw new BadRequestException("입력 인자가 NUll인 것이 존재합니다.");
        }
        this.id = id;
        this.member = member;
        this.date = date;
        this.time = time;
    }

    public ReservationResponseDto toDto() {
        return new ReservationResponseDto(id, member.getName(), date, time);
    }

    public Boolean isHourDuplicated(LocalTime time) {
        if (time.format(DateTimeFormatter.ofPattern("HH")).equals(this.time.format(DateTimeFormatter.ofPattern("HH")))) {
            return true;
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long id;
        private Member member;
        private LocalDate date;
        private LocalTime time;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, member, date, time);
        }
    }
}
