package back.global.config.security.handler;

import back.global.error.ErrorResponse;
import back.global.error.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponse.of(ExceptionCode.HANDLE_ACCESS_DENIED);
        log.warn("Forbidden error happened: {}", accessDeniedException.getMessage());
    }
}
