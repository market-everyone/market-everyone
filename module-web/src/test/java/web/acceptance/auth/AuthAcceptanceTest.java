package web.acceptance.auth;

import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.acceptance.AcceptanceTest;
import web.common.Act;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("소셜 로그인 한다.")
    @Test
    void test() {
        ExtractableResponse<Response> response = Act.request(Method.GET, "/oauth2/authorization/google");
        assertThat(response.statusCode()).isEqualTo(200);
    }

}
