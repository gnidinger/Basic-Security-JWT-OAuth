package back.global.config.security.refreshToken.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class TokenUnsupported extends BusinessException {

    public TokenUnsupported() {
        super(ExceptionCode.TOKEN_UNSUPPORTED.getMessage(), ExceptionCode.TOKEN_UNSUPPORTED);
    }
}