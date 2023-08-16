package wantedpreonboardingbackend.assignment.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private String email;
    private String message;
    public static SignInResponse signInSuccessResponse(String email) {
        String message = email + " 님, 로그인이 완료되었습니다.";
        return new SignInResponse(email, message);
    }
}
