package wantedpreonboardingbackend.assignment.util.exception;

import org.springframework.http.HttpStatus;

public class UserBadRequestException extends RuntimeException{

    private final HttpStatus httpStatus;

    public UserBadRequestException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public UserBadRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
