package back.global.error.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    INVALID_INPUT_VALUE(400, "잘못된 입력입니다."),
    ENTITY_NOT_FOUND(400, "엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),
    HANDLE_ACCESS_DENIED(403, "접근이 거부 되었습니다."),
    METHOD_NOT_ALLOWED(405, "허용하지 않는 HTTP 메소드입니다."),

    /* JWT */
    ACCESS_TOKEN_NOT_FOUND(404,"액세스토큰을 찾을 수 없습니다."),
    TOKEN_EXPIRED(400, "Token Expired"),
    TOKEN_INVALID(400, "Token Invalid"),
    TOKEN_SIGNATURE_INVALID(400, "Token Signature Invalid"),
    TOKEN_MALFORMED(400, "Token Malformed"),
    TOKEN_UNSUPPORTED(400, "Token Unsupported"),
    TOKEN_ILLEGAL_ARGUMENT(400, "Token Illegal Argument"),

    /* Member */
    LOCATION_NOT_CREATED_YET(404,"아직 주거래 동네를 설정하지 않았습니다."),
    UNAUTHORIZIZED_ACCESS(401, "접근 권한 인증이 되지 않았습니다."),
    MEMBER_HAS_BOOK_ON_LOAN(404, "대여중인 책이 있습니다"),
    MEMBER_NOT_FOUND(404, "해당 유저를 찾지 못했습니다.");

    private int status;
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

