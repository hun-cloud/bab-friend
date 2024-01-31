package babfriend.api.board.controller;

import babfriend.api.board.dto.*;
import babfriend.api.board.service.BoardService;
import babfriend.api.common.ResponseDto;
import babfriend.api.common.exception.CredentialsException;
import babfriend.api.user.entity.User;
import babfriend.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "게시글 API", description = "게시글 관련 API")
@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @Operation(summary = "게시글 리스트 API")
    @GetMapping
    public ResponseDto<BoardListResponseDto> boards(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseDto.success(boardService.list(pageable));
    }

    @Operation(summary = "게시글 작성 API")
    @PostMapping("/posts")
    public ResponseDto post(HttpServletRequest request, @RequestBody BoardtDto boardtDto) {
        User user = userService.findUser(request);
        boardService.postBoard(user, boardtDto);
        return ResponseDto.success();
    }

    @Operation(summary = "게시글 상세 API")
    @GetMapping("/{boardId}")
    public ResponseDto<BoardResponseDto> boardDetail(@PathVariable("boardId") Long boardId) {
        BoardResponseDto board = boardService.findById(boardId);

        return ResponseDto.success(board);
    }

    @Operation(summary = "게시글 수정 API")
    @PatchMapping("/{boardId}")
    public ResponseDto boardUpdate(@PathVariable("boardId") Long boardId,
                                   @RequestBody BoardUpdateDto boardUpdateDto,
                                   HttpServletRequest request) {

        User user = userService.findUser(request);
        BoardResponseDto board = boardService.findById(boardId);

        if (!board.getWriterEmail().equals(user.getEmail())) {
            throw new CredentialsException();
        }

        boardService.update(boardId, boardUpdateDto);


        return ResponseDto.success();
    }

    @Operation(summary = "게시글 삭제 API")
    @DeleteMapping("/{boardId}")
    public ResponseDto boardDetail(@PathVariable("boardId") Long boardId, HttpServletRequest request) {
        User user = userService.findUser(request);
        BoardResponseDto board = boardService.findById(boardId);

        if (!board.getWriterEmail().equals(user.getEmail())) {
            throw new CredentialsException();
        }

        boardService.deleteById(boardId);

        return ResponseDto.success();
    }

    @Operation(summary = "게시글 fix API (약속 결정) 토글")
    @PostMapping("/{boardId}/fix")
    public ResponseDto boardFix(@PathVariable("boardId") Long boardId, HttpServletRequest request) {
        User user = userService.findUser(request);
        BoardResponseDto board = boardService.findById(boardId);

        if (!board.getWriterEmail().equals(user.getEmail())) {
            throw new CredentialsException();
        }

        boardService.updateFix(board.getId());

        return ResponseDto.success();
    }

}
