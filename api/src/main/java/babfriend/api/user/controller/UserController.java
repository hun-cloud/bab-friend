package babfriend.api.user.controller;

import babfriend.api.auth.dto.TokenDto;
import babfriend.api.board.dto.BoardJoinMeetingDto;
import babfriend.api.board.dto.BoardsSimpleDto;
import babfriend.api.board.service.BoardService;
import babfriend.api.common.ResponseDto;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.dto.UserMeetingDto;
import babfriend.api.user.dto.UserUpdateDto;
import babfriend.api.user.entity.User;
import babfriend.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자 정보 API", description = "사용자 관련 API")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;
    private final BoardService boardService;

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
    @PatchMapping(value = "/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto updateUserInfo(HttpServletRequest request, @ParameterObject @ModelAttribute UserUpdateDto userUpdateDto) {

        userService.updateInfo(request, userUpdateDto);

        return ResponseDto.success();
    }

    @Operation(summary = "내가 참여한 식사 리스트 API - 미완성")
    @GetMapping("/meetings")
    public ResponseDto<List<BoardsSimpleDto>> userMeetingList(HttpServletRequest request) {
        User user = userService.findUser(request);

        return ResponseDto.success(boardService.getJoinMeeting(user));
    }
}
