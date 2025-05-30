package roomescape.model.entity;

import roomescape.model.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Reservation {

    private static final AtomicInteger count = new AtomicInteger(1);
    private final int id;
    private final Member member;
    private final LocalDate date;
    private final LocalTime time;

    private Reservation(final Member member, final LocalDate date, final LocalTime time) {
        this.id = count.getAndIncrement();
        this.member = member;
        this.date = date;
        this.time = time;
    }

    public ReservationResponseDto toDto() {
        return new ReservationResponseDto(id, member.getName(), date, time);
    }

    public int getId() {
        return id;
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
        private Member member;
        private LocalDate date;
        private LocalTime time;

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
            return new Reservation(member, date, time);
        }
    }
}
