package babfriend.api.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.FastByteArrayOutputStream;

@Getter
public class ResponseDto<T> {

    private final int statusCode;
    private final String responseMessage;
    private final T data;

    @Builder
    private ResponseDto(int statusCode, String responseMessage, T data) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public static ResponseDto success() {
        return ResponseDto.builder()
                .statusCode(StatusCode.OK)
                .build();
    }

    public static <T> ResponseDto<T> success(T body) {
        return ResponseDto.<T>builder()
                .statusCode(StatusCode.OK)
                .data(body)
                .build();
    }

    public static ResponseDto fail() {
        return ResponseDto.builder()
                .statusCode(StatusCode.INTERNAL_SERVER_ERROR)
                .responseMessage(ResponseMessage.FAIL)
                .build();
    }

    public static ResponseDto fail(int code, String msg) {
        return ResponseDto.builder()
                .statusCode(code)
                .responseMessage(msg)
                .build();
    }
}
