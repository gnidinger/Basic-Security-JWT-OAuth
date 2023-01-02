package back.global.config.security.refreshToken.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class TokenInvalid extends BusinessException {

    public TokenInvalid() {
        super(ExceptionCode.TOKEN_INVALID.getMessage(), ExceptionCode.TOKEN_INVALID);
    }
}