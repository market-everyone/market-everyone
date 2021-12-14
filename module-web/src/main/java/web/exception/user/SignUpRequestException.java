package web.exception.user;

import org.springframework.http.HttpStatus;
import web.exception.ApplicationException;

public class SignUpRequestException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "회원가입 도중 오류가 발생하였습니다.";

    public SignUpRequestException() {
        this(HTTP_STATUS, MESSAGE);
    }

    protected SignUpRequestException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
