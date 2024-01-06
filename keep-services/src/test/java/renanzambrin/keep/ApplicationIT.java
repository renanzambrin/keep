package renanzambrin.keep;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import renanzambrin.keep.shared.infrastructure.AbstractIntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

class ApplicationIT extends AbstractIntegrationTest {

    @Test
    @DisplayName("Verify health endpoint")
    void healthCheck() {
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/health")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Verify liveness endpoint")
    void livenessCheck() {
        given()
                .contentType("application/json")
                .when()
                .get("/health/liveness")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("status", is("UP"));
    }

    @Test
    @DisplayName("Verify readiness endpoint")
    void readinessCheck() {
        given()
                .contentType("application/json")
                .when()
                .get("/health/readiness")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("status", is("UP"));
    }

    @Test
    @DisplayName("Verify info endpoint")
    void infoCheck() {
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/info")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value());
    }

}