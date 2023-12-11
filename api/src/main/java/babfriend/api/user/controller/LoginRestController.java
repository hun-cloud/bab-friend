package babfriend.api.user.controller;


import babfriend.api.common.ResponseDto;
import babfriend.api.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginRestController {

    @GetMapping("/test")
    public ResponseDto test() {
        return ResponseDto.builder()
                .statusCode(StatusCode.OK)
                .responseMessage("123123")
                .build();
    }
}
