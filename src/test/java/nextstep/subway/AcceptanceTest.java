package nextstep.subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }
    }

    public static ExtractableResponse<Response> get(String path) {
        return RestAssured.given().log().all()
            .when().get(path)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> post(String path, T requestBody) {
        return RestAssured.given().log().all()
            .body(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> delete(String path) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete(path)
            .then().log().all()
            .extract();
    }

}
