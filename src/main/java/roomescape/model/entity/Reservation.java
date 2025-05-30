package roomescape.model.entity;

import roomescape.model.dto.ReservationDto;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Reservation {

    private static AtomicInteger count = new AtomicInteger(1);
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


    public static Reservation of(final Member member, final LocalDate date, final LocalTime time) {
        return new Reservation(member, date, time);
    }

    public ReservationDto toDto() {
        return new ReservationDto(id, member.getName(), date, time);
    }
}
