package back.global.config.security.filter;

import back.global.config.security.cookieManager.CookieManager;
import back.global.config.security.dto.LoginRequestDto;
import back.global.config.security.dto.LoginResponseDto;
import back.global.config.security.userDetails.AuthUser;
import back.global.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final CookieManager cookieManager;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto.PostDto loginRequest= objectMapper.readValue(request.getInputStream(), LoginRequestDto.PostDto.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authResult.getPrincipal();
        TokenDto tokenDto = jwtProvider.generateTokenDto(authUser);

        String refreshToken = tokenDto.getRefreshToken();

        ResponseCookie cookie = cookieManager.createCookie("refreshToken", refreshToken);

        response.setHeader("Set-Cookie", cookie.toString());
        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

        ObjectMapper objectMapper = new ObjectMapper();
        String userLoginResponse = objectMapper.writeValueAsString(
                LoginResponseDto.ResponseDto.of(authUser)
        );
        // response body에 user의 id, username을 담아서 보내준다.
        response.getWriter().write(
                userLoginResponse
        );

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
