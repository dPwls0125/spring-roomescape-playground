package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testHomePageReturn() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .contentType(ContentType.HTML)
                .statusCode(200);
    }

    @Test
    void testAccessReservationPageAndGetReservationList() {

        RestAssured.given()
                .when().log().all()
                .get("/reservation")
                .then()
                .log().all()
                .contentType(ContentType.HTML)
                .statusCode(200);

        RestAssured.given().log().all()
                .get("/reservations")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(0));
    }

    @Test
    void testCreateAndDeleteReservation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));

    }

    @Test
    void testExceptionHandling() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        // 필요한 인자가 없는 경우
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 오단계() {

        SoftAssertions softly = new SoftAssertions();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            softly.assertThat(connection).isNotNull();
            softly.assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            softly.assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
            softly.assertAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
