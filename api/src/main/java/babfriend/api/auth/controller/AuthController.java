package babfriend.api.auth.controller;

import babfriend.api.auth.dto.KakaoLoginResponseDto;
import babfriend.api.auth.dto.TokenDto;
import babfriend.api.auth.service.AuthService;
import babfriend.api.common.ResponseDto;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API", description = "로그인 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "토큰 발급 API")
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseDto<?> kakaoCallback(@RequestParam("code") String code) {

        KakaoLoginResponseDto kakaoLoginResponseDto = authService.getKakaoToken(code);

        UserDto userDto = authService.getUserInfo(kakaoLoginResponseDto);

        userService.join(userDto);

        TokenDto tokenDto = authService.createToken(userDto);

        return ResponseDto.success(tokenDto);
    }

    @Operation(summary = "액세스 토큰 재발급 API")
    @PatchMapping("/auth/reissue")
    public ResponseDto<?> reissue(@RequestBody TokenDto tokenDto) {
        String result = authService.reissueAccessToken(tokenDto);

        return ResponseDto.success(result);
    }

    @Operation(summary = "토큰 테스트 API")
    @GetMapping("/oauth2/kakao")
    public ResponseDto<?> test() {
        System.out.println("in");
        return ResponseDto.success();
    }

}
