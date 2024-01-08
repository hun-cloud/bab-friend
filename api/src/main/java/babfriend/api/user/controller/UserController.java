package babfriend.api.user.controller;

import babfriend.api.auth.dto.TokenDto;
import babfriend.api.common.ResponseDto;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.dto.UserUpdateDto;
import babfriend.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 정보 API", description = "사용자 관련 API")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저정보 API")
    @GetMapping("/info")
    public ResponseDto<UserDto> userInfo(HttpServletRequest request) {
        UserDto userDto = userService.userInfo(request);

        return ResponseDto.success(userDto);
    }

    @Operation(summary = "유저정보 상세 API")
    @GetMapping("/info/detail")
    public ResponseDto<UserDto> userInfoDetail(HttpServletRequest request) {
        UserDto userDto = userService.userInfo(request);

        return ResponseDto.success(userDto);
    }

    @Operation(summary = "유저 정보 업데이트 API")
    @PatchMapping("/info")
    public ResponseDto updateUserInfo(HttpServletRequest request, @ModelAttribute UserUpdateDto userUpdateDto) {

        userService.updateInfo(request, userUpdateDto);

        return ResponseDto.success();
    }
}
