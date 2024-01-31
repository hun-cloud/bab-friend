package babfriend.api.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    REDIS_NOT_FOUND(404, "404", "레디스에서 키에 대한 값을 찾지 못했습니다"),
    TOKEN_IS_NOT_SAME(1000, "1000", "토큰이 올바르지 않습니다."),
    ACCESS_TOKEN_IS_EXPIRED(1001, "1001", "액세스 토큰이 만료되었습니다."),
    REFRESH_TOKEN_IS_EXPIRED(1002, "1002", "리프레시 토큰이 만료되었습니다."),
    CREDENTIALS_IS_FAIL(401, "401", "자격증명에 실패하였습니다.");

    private final int status;
    private final String code;
    private final String msg;
}
