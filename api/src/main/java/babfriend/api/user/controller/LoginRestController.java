package babfriend.api.user.controller;


import babfriend.api.common.ResponseDto;
import babfriend.api.common.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 API", description = "로그인 관련 API")
@RestController
@RequiredArgsConstructor
public class LoginRestController {

    @Operation(summary = "테스트")
    @GetMapping("/test")
    public ResponseDto test() {
        return ResponseDto.builder()
                .statusCode(StatusCode.OK)
                .responseMessage("123123")
                .build();
    }

    @GetMapping("/authTest")
    public ResponseDto test2(){
        return ResponseDto.builder()
                .statusCode(StatusCode.OK)
                .responseMessage("123123")
                .build();
    }
}
