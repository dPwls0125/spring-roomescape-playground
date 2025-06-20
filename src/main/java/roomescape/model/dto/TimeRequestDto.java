package roomescape.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class TimeRequestDto {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

}
