package wantedpreonboardingbackend.assignment.util.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(UserBadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
