package babfriend.api.common.config;

import babfriend.api.common.ResponseDto;
import babfriend.api.common.StatusCode;
import babfriend.api.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalControllerErrorAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseDto CustomException(CustomException e) {
        int errorStatus = e.getErrorCode().getStatus();

        throw new ResponseStatusException(HttpStatus.valueOf(errorStatus), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDto accessDeniedException(AccessDeniedException e) {
        return ResponseDto.builder()
                .statusCode(StatusCode.FORBIDDEN)
                .responseMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto responseStatusException(ResponseStatusException e) {
        return ResponseDto.builder()
                .statusCode(e.getStatusCode().value())
                .responseMessage(e.getReason())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto exception(Exception e) {
        return ResponseDto.builder()
                .statusCode(StatusCode.INTERNAL_SERVER_ERROR)
                .responseMessage(e.getMessage())
                .build();
    }
}
