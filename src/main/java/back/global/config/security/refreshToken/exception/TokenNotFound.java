package back.global.config.security.refreshToken.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class TokenNotFound extends BusinessException {

    public TokenNotFound() {
        super(ExceptionCode.ACCESS_TOKEN_NOT_FOUND.getMessage(), ExceptionCode.ACCESS_TOKEN_NOT_FOUND);
    }
}