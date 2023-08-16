package wantedpreonboardingbackend.assignment.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wantedpreonboardingbackend.assignment.util.constants.ErrorMessages;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    boolean success;
    T data;
    Error error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public ResponseDto(T data, String errorMessages) {
        if (data != null) {
            this.success = true;
        } else {
            this.success = false;
            this.error = new Error(errorMessages);
        }

        this.data = data;
        this.error = new Error(errorMessages);
    }

    public ResponseDto(T data) {
        this.success = true;
        this.data = data;
        this.error = null;
    }


    @Getter
    public static class Error {

        private String message;

        public Error(String errorMessages) {
            this.message = errorMessages.toString();
        }
    }

}
