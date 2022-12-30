package back.global.config.security.handler;

import back.domain.refreshToken.entity.RefreshToken;
import back.domain.refreshToken.repository.RefreshTokenRepository;
import back.global.config.security.userDetails.AuthMember;
import back.global.error.ErrorResponse;
import back.global.error.exception.ExceptionCode;
import com.google.gson.Gson;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    public MemberAuthenticationSuccessHandler(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        AuthMember authMember = (AuthMember) authentication.getPrincipal();

        String refreshToken = response.getHeader("Set-Cookie").substring(13).split(";")[0];

        // RefreshToken 저장
        RefreshToken refresh = RefreshToken.builder()
                .key(authMember.getMemberId())
                .value(refreshToken)
                .build();

        refreshTokenRepository.save(refresh);
    }
}