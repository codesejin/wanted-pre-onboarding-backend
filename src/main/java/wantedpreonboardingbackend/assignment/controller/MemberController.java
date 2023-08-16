package wantedpreonboardingbackend.assignment.controller;

import wantedpreonboardingbackend.assignment.domain.dto.req.UserRequest;
import wantedpreonboardingbackend.assignment.domain.dto.res.SignInResponse;
import wantedpreonboardingbackend.assignment.domain.dto.res.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wantedpreonboardingbackend.assignment.service.MemberService;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api-prefix}/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "유저 회원가입",
            tags = {"회원가입(유효성 검사 - 이메일 조건: @ 포함, 비밀번호 조건: 8자 이상)"})
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody final UserRequest userRequest) {
        SignUpResponse signUpResponse = memberService.signUp(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @Operation(summary = "유저 로그인",
            tags = {"로그인(이메일과 비밀번호가 올바르면 JWT토큰 반환)"})
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(value = "/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody final UserRequest userRequest,  HttpServletResponse httpServletResponse) throws Exception {
        SignInResponse signInResponse = memberService.signIn(userRequest, httpServletResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(signInResponse);
    }
}
