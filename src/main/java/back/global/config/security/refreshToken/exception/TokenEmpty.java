package back.global.config.security.refreshToken.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class TokenEmpty extends BusinessException {

    public TokenEmpty() {
        super(ExceptionCode.TOKEN_ILLEGAL_ARGUMENT.getMessage(), ExceptionCode.TOKEN_ILLEGAL_ARGUMENT);
    }
}
