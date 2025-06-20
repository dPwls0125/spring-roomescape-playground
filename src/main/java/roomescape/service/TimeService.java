package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.model.dto.TimeRequestDto;
import roomescape.model.dto.TimeResponseDto;
import roomescape.model.entity.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeResponseDto addTime(TimeRequestDto timeRequestDto) {
        long keyValue = timeRepository.save(timeRequestDto);
        return new TimeResponseDto(keyValue, timeRequestDto.getTime());
    }

    public List<TimeResponseDto> findAllTimes() {
        return timeRepository.findAll().stream().map(Time::toDto).toList();
    }

    public void deleteTimes(long id) {
        int result = timeRepository.delete(id);
        if (result == 0) {
            throw new BadRequestException("id 값과 일치하는 등록된 시간이 일치하지 않습니다.");
        }
    }

}
