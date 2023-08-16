package domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private String email;
    private String message;
    private String token;
    public static SignInResponse signInSuccessResponse(String email, String token) {
        String message = email + " 님, 로그인이 완료되었습니다.";
        return new SignInResponse(email, message, token);
    }
}
