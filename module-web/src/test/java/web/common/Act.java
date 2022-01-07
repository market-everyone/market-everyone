package web.common;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class Act {

    public static ExtractableResponse<Response> request(Method method,
                                                        String uri) {
         return RestAssured.given().log().all()
                .when().request(method, uri)
                .then().log().all()
                .extract();
    }

//    public static ExtractableResponse<Response> request(Method method,
//                                                        String uri) {
//        return RestAssured.given().log().all()
//                .when().request(method, uri)
//                .then().log().all()
//                .extract();
//    }
}
