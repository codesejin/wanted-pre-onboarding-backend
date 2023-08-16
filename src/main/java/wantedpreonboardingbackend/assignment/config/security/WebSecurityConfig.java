package wantedpreonboardingbackend.assignment.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import wantedpreonboardingbackend.assignment.config.jwt.JwtAccessDeniedHandler;
import wantedpreonboardingbackend.assignment.config.jwt.JwtAuthenticationEntryPoint;
import wantedpreonboardingbackend.assignment.config.jwt.TokenProvider;
import wantedpreonboardingbackend.assignment.service.UserDetailsServiceImpl;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    @Value("${jwt.secret}")
    String SECRET_KEY;

    private final UserDetailsServiceImpl userDetailsService;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf().disable();
        // exception handling 할 때 커스텀 클래스를 추가
        http
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // jwt 자체에 문제가 있는거
                .accessDeniedHandler(jwtAccessDeniedHandler); // jwt는 정상인데 접근하려는 url의 권한이 다를 때

        // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()  //폼로그인 안쓰겠다
                .httpBasic().disable()  //기본 인증방식 사용 X
                // HttpServletRequest 사용하는 요청들에 대한 접근 제한을 사용
                .authorizeRequests()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/", "/**").permitAll() // 헬스체크용
                .antMatchers("/api/v1/*").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                // swagger
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest().authenticated();
        // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
        http
                .apply(new JwtSecurityConfig(SECRET_KEY, tokenProvider, userDetailsService));
        return http.build();
    }

    @Bean//패스워드인코드를 IoC에 등록 => 스프링이 Ioc 알아서 이걸 찾아서 DB랑 암호화해서 비교
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}
