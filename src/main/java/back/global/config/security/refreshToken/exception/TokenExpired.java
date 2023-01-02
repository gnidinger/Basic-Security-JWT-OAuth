package back.global.config.security.refreshToken.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class TokenExpired extends BusinessException {

    public TokenExpired() {
        super(ExceptionCode.TOKEN_EXPIRED.getMessage(),ExceptionCode.TOKEN_EXPIRED);
    }
}
