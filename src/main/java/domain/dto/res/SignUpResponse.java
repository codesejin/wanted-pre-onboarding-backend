package domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {
    private Long id;
    private String email;
    private String message;

    public static SignUpResponse createSuccessResponse(Long id, String email) {
        String message = email + " 님, 회원가입이 완료되었습니다.";
        return new SignUpResponse(id, email, message);
    }
}
