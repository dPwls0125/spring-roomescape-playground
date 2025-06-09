package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.BadRequestException;
import roomescape.model.dto.ReservationRequestDto;
import roomescape.model.entity.Reservation;

import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {

        RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
            Reservation reservation = Reservation.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .date(resultSet.getDate("date").toLocalDate())
                    .time(resultSet.getTime("time").toLocalTime())
                    .build();
            return reservation;
        };

        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public long saveReservation(final ReservationRequestDto requestDto) {
        List<Reservation> reservations = findAllReservations();
        boolean isDuplicatedTime = reservations.stream()
                .anyMatch(reservation -> reservation.isHourDuplicated(requestDto.getTime()));

        if (isDuplicatedTime) throw new BadRequestException("이미 존재하는 예약과 시간이 중복됩니다.");

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, requestDto.getName());
            ps.setObject(2, requestDto.getDate());
            ps.setObject(3, requestDto.getTime());
            return ps;
        }, kh);

        long newId = kh.getKey().longValue();
        return newId;
    }

    public void deleteReservation(final Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

}
