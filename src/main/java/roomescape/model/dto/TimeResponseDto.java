package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeResponseDto {

    private long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public TimeResponseDto(final long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }
}
