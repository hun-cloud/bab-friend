package babfriend.api.common.config;

import babfriend.api.common.ResponseDto;
import babfriend.api.common.StatusCode;
import babfriend.api.common.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
        e.printStackTrace();
        int errorStatus = e.getErrorCode().getStatus();

        throw new ResponseStatusException(HttpStatus.valueOf(errorStatus), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDto accessDeniedException(AccessDeniedException e) {
        e.printStackTrace();
        return ResponseDto.builder()
                .statusCode(StatusCode.FORBIDDEN)
                .responseMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto responseStatusException(ResponseStatusException e) {
        e.printStackTrace();
        return ResponseDto.builder()
                .statusCode(e.getStatusCode().value())
                .responseMessage(e.getReason())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto entityNotFoundException(Exception e) {
        e.printStackTrace();

        return ResponseDto.builder()
                .statusCode(StatusCode.NOT_FOUND)
                .responseMessage(e.getMessage())
                .build();
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto exception(Exception e) {
        e.printStackTrace();

        return ResponseDto.builder()
                .statusCode(StatusCode.INTERNAL_SERVER_ERROR)
                .responseMessage(e.getMessage())
                .build();
    }
}
