package back.global.config.security.handler;

import back.global.config.security.refreshToken.entity.RefreshToken;
import back.global.config.security.refreshToken.repository.RefreshTokenRepository;
import back.global.config.security.userDetails.AuthUser;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    public UserAuthenticationSuccessHandler(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        String refreshToken = response.getHeader("Set-Cookie").substring(13).split(";")[0];

        // RefreshToken 저장
        RefreshToken refresh = RefreshToken.builder()
                .key(authUser.getId())
                .value(refreshToken)
                .build();

        refreshTokenRepository.save(refresh);
    }
}