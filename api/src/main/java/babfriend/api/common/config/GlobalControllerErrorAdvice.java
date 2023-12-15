package babfriend.api.common.config;

import babfriend.api.common.ResponseDto;
import babfriend.api.common.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalControllerErrorAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseDto accessDeniedException(AccessDeniedException e) {
        return ResponseDto.builder()
                .statusCode(StatusCode.FORBIDDEN)
                .responseMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto exception(Exception e) {
        return ResponseDto.builder()
                .statusCode(StatusCode.INTERNAL_SERVER_ERROR)
                .responseMessage(e.getMessage())
                .build();
    }
}
