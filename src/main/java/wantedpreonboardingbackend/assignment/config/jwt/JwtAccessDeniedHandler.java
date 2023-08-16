package wantedpreonboardingbackend.assignment.config.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import wantedpreonboardingbackend.assignment.util.ResponseDto;
import wantedpreonboardingbackend.assignment.util.constants.ErrorMessages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 필요한 권한이 없이 접근하려 할때 403
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        new ResponseDto<>(null, ErrorMessages.REQUIRE_AUTHORITY)));
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
