package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationRequestDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotNull(message = "날짜는 필수 입력 항목입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "시간 아이디는 필수 항목입니다.")
    private Long timeId;


    @JsonCreator
    public ReservationRequestDto(final String name, final LocalDate date, @JsonProperty("time") final String timeId) {
        this.name = name;
        this.date = date;
        this.timeId = Long.parseLong(timeId);
    }
}
