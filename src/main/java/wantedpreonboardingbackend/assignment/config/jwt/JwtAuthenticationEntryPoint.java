package wantedpreonboardingbackend.assignment.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import wantedpreonboardingbackend.assignment.util.ResponseDto;
import wantedpreonboardingbackend.assignment.util.constants.ErrorMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        new ResponseDto<>(null, ErrorMessages.BAD_TOKEN)));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
