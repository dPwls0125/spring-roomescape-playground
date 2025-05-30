package roomescape.model.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Member {
    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    private int id;
    private String name;

    public Member(final String name) {
        this.id = atomicInteger.getAndIncrement();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
