package babfriend.api.board.controller;

import babfriend.api.board.dto.BoardsSimpleDto;
import babfriend.api.board.dto.BoardtDto;
import babfriend.api.board.service.BoardService;
import babfriend.api.common.ResponseDto;
import babfriend.api.user.entity.User;
import babfriend.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "게시글 API", description = "게시글 관련 API")
@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @Operation(summary = "게시글 리스트 API - 미완성")
    @GetMapping
    public List<BoardsSimpleDto> boards() {

        return null;
    }

    @Operation(summary = "게시글 작성 API")
    @PostMapping("/posts")
    public ResponseDto post(HttpServletRequest request, @RequestBody BoardtDto boardtDto) {
        User user = userService.findUser(request);
        boardService.postBoard(user, boardtDto);
        return ResponseDto.success();
    }

}
