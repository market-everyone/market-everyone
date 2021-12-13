package web.exception.auth;

import org.springframework.http.HttpStatus;
import web.exception.ApplicationException;

public class UnsupportedOauthVendorException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "지원하지 않는 oauth 로그인입니다.";

    public UnsupportedOauthVendorException() {
        this(HTTP_STATUS, MESSAGE);
    }

    protected UnsupportedOauthVendorException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
