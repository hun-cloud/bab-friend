package babfriend.api.common.exception;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CustomException extends RuntimeException implements Serializable {

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
}
