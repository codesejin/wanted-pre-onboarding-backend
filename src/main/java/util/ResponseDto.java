package util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import util.constants.ErrorMessages;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    boolean success;
    T data;
    Error error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public ResponseDto(T data, ErrorMessages errorMessages) {
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

        public Error(ErrorMessages errorMessages) {
            this.message = errorMessages.toString();
        }
    }

}
