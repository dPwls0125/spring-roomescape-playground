package roomescape.model.entity;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import roomescape.model.dto.TimeResponseDto;

import java.time.LocalTime;

@RequiredArgsConstructor
@Builder
public class Time {

    private final long id;
    private final LocalTime time;

    public TimeResponseDto toDto() {
        return new TimeResponseDto(id, time);
    }

}
