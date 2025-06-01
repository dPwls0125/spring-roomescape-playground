package roomescape.model.entity;

import roomescape.exception.BadRequestException;

import java.util.Objects;

public class Member {
    private final String name;

    public Member(final String name) {
        if (name == null || name.isBlank()) throw new BadRequestException("이름이 null이면 안됩니다.");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return Objects.equals(getName(), member.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
