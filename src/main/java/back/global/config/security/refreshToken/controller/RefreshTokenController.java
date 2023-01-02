package back.global.config.security.refreshToken.controller;

import back.global.config.security.refreshToken.entity.RefreshToken;
import back.domain.user.entity.User;
import back.domain.user.service.UserService;
import back.global.config.security.cookieManager.CookieManager;
import back.global.config.security.jwtTokenizer.JwtTokenizer;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final JwtTokenizer jwtTokenizer;
    private final UserService userService;
    private final CookieManager cookieManager;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = cookieManager.outCookie(request, "refreshToken");

        try {
            jwtTokenizer.verifySignature(refreshToken);

            RefreshToken findRefreshToken = jwtTokenizer.getRefreshToken(refreshToken);
            if(findRefreshToken == null)
                response.sendError(401, "Refresh Token Not Found");

            User user = userService.findUserById(findRefreshToken.getKey());
            String accessToken = jwtTokenizer.delegateAccessToken(user);
            response.setHeader("Authorization", "Bearer " + accessToken);
        } catch (ExpiredJwtException ee) {
            jwtTokenizer.removeRefreshToken(refreshToken);
            response.sendError(401, "Refresh Token Expired");
        }
    }
}
