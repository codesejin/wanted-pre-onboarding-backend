package service;

import config.JwtTokenProvider;
import domain.User;
import domain.UserRepository;
import domain.dto.req.UserRequest;
import domain.dto.res.SignInResponse;
import domain.dto.res.SignUpResponse;
import util.constants.ErrorMessages;
import util.exception.UserBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입
    public SignUpResponse signUp(UserRequest userRequest) {

        if (userRequest.isEmailValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_EMAIL);
        } else if (userRequest.isPasswordValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_PASSWORD);
        } else {
            User createUser = User.builder()
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .build();
            User savedUser = userRepository.save(createUser);
            return SignUpResponse.createSuccessResponse(savedUser.getId(), savedUser.getEmail());
        }
    }

    // 로그인
    public SignInResponse signIn(UserRequest userRequest) throws Exception {

        if (userRequest.isEmailValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_EMAIL);
        } else if (userRequest.isPasswordValid()) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_PASSWORD);
        }

        Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());
        if (optionalUser.isEmpty()) {
            throw new UserBadRequestException(ErrorMessages.NOT_FOUND_USER); // 유저를 찾을 수 없는 경우
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new UserBadRequestException(ErrorMessages.CHECK_YOUR_PASSWORD); // 비밀번호가 일치하지 않는 경우
        }
        if (optionalUser.isPresent()) {
            String jwtToken = getJwtToken(userRequest.getEmail(), user.getPassword());

            return SignInResponse.signInSuccessResponse(optionalUser.get().getEmail(), jwtToken);
        }
        return null;
    }


    private String getJwtToken(String email, String pwd) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);
        // authenticate 메서드가 실행이 될 때 UserDetailsServiceImpl 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }
}
