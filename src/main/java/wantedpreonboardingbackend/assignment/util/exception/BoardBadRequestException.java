package wantedpreonboardingbackend.assignment.util.exception;

import org.springframework.http.HttpStatus;

public class BoardBadRequestException extends RuntimeException{

    private final HttpStatus httpStatus;

    public BoardBadRequestException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public BoardBadRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
