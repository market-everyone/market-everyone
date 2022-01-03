package web.acceptance.board;

import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.acceptance.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static web.common.Act.request;

public class BoardAcceptanceTest extends AcceptanceTest {

    @DisplayName("공지사항")
    @Test
    void notice() {
        ExtractableResponse<Response> response = request(Method.GET, "/board/notice");
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("자주 묻는 질문")
    @Test
    void faq() {
        ExtractableResponse<Response> response = request(Method.GET, "/board/faq");
        assertThat(response.statusCode()).isEqualTo(200);
    }

}
