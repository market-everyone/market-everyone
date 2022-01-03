package web.acceptance.user;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.acceptance.AcceptanceTest;
import web.user.controller.dto.request.UserSignUpRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void test() {
        ExtractableResponse<Response> response =
                RestAssured
                        .given().log().all().body(new UserSignUpRequest("junslee@test.com", "password12", "password12", "junslee"))
                        .when().request(Method.POST, "/users/signup")
                        .then().extract();

        assertThat(response.statusCode()).isEqualTo(200);
    }
}
