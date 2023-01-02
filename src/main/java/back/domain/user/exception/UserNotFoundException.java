package back.domain.user.exception;

import back.global.error.exception.BusinessException;
import back.global.error.exception.ExceptionCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ExceptionCode.USER_NOT_FOUND.getMessage(), ExceptionCode.USER_NOT_FOUND);
    }
}

