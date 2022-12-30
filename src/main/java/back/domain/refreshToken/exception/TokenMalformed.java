package back.domain.refreshToken.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class TokenMalformed extends BusinessException {

    public TokenMalformed() {
        super(ExceptionCode.TOKEN_MALFORMED.getMessage(), ExceptionCode.TOKEN_MALFORMED);
    }
}