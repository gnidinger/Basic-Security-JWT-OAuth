package back.global.config.security.refreshToken.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class TokenSignatureInvalid extends BusinessException {

    public TokenSignatureInvalid() {
        super(ExceptionCode.TOKEN_SIGNATURE_INVALID.getMessage(), ExceptionCode.TOKEN_SIGNATURE_INVALID);
    }
}