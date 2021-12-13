package web.exception.user;

import org.springframework.http.HttpStatus;
import web.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public UserNotFoundException() {
        this(HTTP_STATUS, MESSAGE);
    }

    protected UserNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
