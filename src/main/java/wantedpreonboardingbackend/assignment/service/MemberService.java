package wantedpreonboardingbackend.assignment.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import wantedpreonboardingbackend.assignment.config.jwt.TokenProvider;
import wantedpreonboardingbackend.assignment.domain.Member;
import wantedpreonboardingbackend.assignment.domain.MemberRepository;
import wantedpreonboardingbackend.assignment.domain.dto.req.UserRequest;
import wantedpreonboardingbackend.assignment.domain.dto.res.SignInResponse;
import wantedpreonboardingbackend.assignment.domain.dto.res.SignUpResponse;
import wantedpreonboardingbackend.assignment.util.constants.ErrorMessages;
import wantedpreonboardingbackend.assignment.util.exception.UserBadRequestException;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입
    @Transactional
    public SignUpResponse signUp(UserRequest userRequest) {

        if (!userRequest.isEmailValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_EMAIL, HttpStatus.BAD_REQUEST);
        } else if (!userRequest.isPasswordValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
        } else {
            Member createMember = Member.builder()
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
//                    .password(userRequest.getPassword())
                    .build();
            Member savedMember = memberRepository.save(createMember);
            return SignUpResponse.createSuccessResponse(savedMember.getId(), savedMember.getEmail());
        }
    }

    // 로그인
    public SignInResponse signIn(UserRequest userRequest, HttpServletResponse httpServletResponse) throws Exception {

        if (!userRequest.isEmailValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_EMAIL, HttpStatus.BAD_REQUEST);
        } else if (!userRequest.isPasswordValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        Optional<Member> optionalUser = memberRepository.findByEmail(userRequest.getEmail());
        if (!optionalUser.isPresent()) {
            throw new UserBadRequestException(ErrorMessages.NOT_FOUND_USER, HttpStatus.BAD_REQUEST); // 유저를 찾을 수 없는 경우
        }

        Member member = optionalUser.get();

        if (!passwordEncoder.matches(userRequest.getPassword(), member.getPassword())) {
//        if (!userRequest.getPassword().equals(member.getPassword())) {
            throw new UserBadRequestException(ErrorMessages.NO_MATCH_PASSWORD, HttpStatus.BAD_REQUEST); // 비밀번호가 일치하지 않는 경우
        }

        if (optionalUser.isPresent()) {
            getToken(member, httpServletResponse);
            return SignInResponse.signInSuccessResponse(optionalUser.get().getEmail());
        }
        return null;
    }


    @Transactional
    public void getToken(Member member, HttpServletResponse response) {
        String token = tokenProvider.generateTokenDto(member);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
