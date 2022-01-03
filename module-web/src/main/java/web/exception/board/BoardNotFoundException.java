package web.exception.board;

import org.springframework.http.HttpStatus;
import web.exception.ApplicationException;

public class BoardNotFoundException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public BoardNotFoundException() {
        this(HTTP_STATUS, MESSAGE);
    }

    protected BoardNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
