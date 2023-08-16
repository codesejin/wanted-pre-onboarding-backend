package domain.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "SignUpRequest", description = "이메일, 비밀번호 유저 회원가입 요청 값")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UserRequest {

    private String email;

    private String password;

//    public boolean isValid() {
//        return isEmailValid() && isPasswordValid();
//    }

    public boolean isEmailValid() {
        return email != null && email.contains("@");
    }

    public boolean isPasswordValid() {
        return password != null && password.length() >= 8;
    }

}
