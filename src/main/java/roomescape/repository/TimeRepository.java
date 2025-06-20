package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.dto.TimeRequestDto;
import roomescape.model.entity.Time;

import java.util.List;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingColumns("time")
                .usingGeneratedKeyColumns("id");
    }

    public long save(TimeRequestDto timeReqeustDto) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(timeReqeustDto);
        return simpleJdbcInsert.executeAndReturnKey(param).longValue();
    }

    public List<Time> findAll() {
        RowMapper<Time> timeRowMapper = (resultSet, rowNum) ->
                Time.builder()
                        .id(resultSet.getLong("id"))
                        .time(resultSet.getTime("time").toLocalTime())
                        .build();
        return jdbcTemplate.query("select id, time from time", timeRowMapper);
    }

    public int delete(long id) {
        return jdbcTemplate.update("delete from time where id = ? ", id);
    }
}
