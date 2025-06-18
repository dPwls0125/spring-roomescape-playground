package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.dto.ReservationRequestDto;
import roomescape.model.entity.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {

        Reservation reservation = Reservation.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .date(resultSet.getDate("date").toLocalDate())
                .time(resultSet.getTime("time").toLocalTime())
                .build();

        return reservation;
    };

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public long save(final ReservationRequestDto requestDto) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(requestDto);
        return simpleJdbcInsert.executeAndReturnKey(param).longValue();
    }


    public int delete(final Long id) {
        int result = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return result;
    }
}
