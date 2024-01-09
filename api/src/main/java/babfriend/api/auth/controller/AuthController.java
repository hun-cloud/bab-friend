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
    @GetMapping("/kakao/callback")
    public ResponseDto<TokenDto> kakaoCallback(@RequestParam("code") String code) {
        System.out.println(code);
        KakaoLoginResponseDto kakaoLoginResponseDto = authService.getKakaoToken(code);
        log.info("getUserInfo start");
        UserDto userDto = null;
        try {
            userDto = authService.getUserInfo(kakaoLoginResponseDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("getUserInfo finish");
        userService.join(userDto);

        TokenDto tokenDto = authService.createToken(userDto);

        return ResponseDto.success(tokenDto);
    }

    @Operation(summary = "액세스 토큰 재발급 API")
    @PatchMapping("/auth/reissue")
    public ResponseDto<TokenDto> reissue(@RequestBody TokenDto tokenDto) {
        TokenDto result = authService.reissueAccessToken(tokenDto);

        return ResponseDto.success(result);
    }

    @Operation(summary = "토큰 테스트 API")
    @GetMapping("/oauth2/kakao")
    public ResponseDto<?> test() {
        System.out.println("in");
        return ResponseDto.success();
    }

}
