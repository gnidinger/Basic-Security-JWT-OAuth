package back.global.config.security.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class AccessTokenNotFound extends BusinessException {

    public AccessTokenNotFound() {
        super(ExceptionCode.ACCESS_TOKEN_NOT_FOUND.getMessage(),ExceptionCode.ACCESS_TOKEN_NOT_FOUND);
    }
}
