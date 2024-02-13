package babfriend.api.review.controller;

import babfriend.api.board.dto.BoardResponseDto;
import babfriend.api.board.entity.Board;
import babfriend.api.board.repository.BoardRepository;
import babfriend.api.board.service.BoardService;
import babfriend.api.common.ResponseDto;
import babfriend.api.review.dto.EatReviewDto;
import babfriend.api.review.dto.EatReviewPossibleDto;
import babfriend.api.review.entity.EatReview;
import babfriend.api.review.service.EatReviewService;
import babfriend.api.user.dto.EatReviewResponseDto;
import babfriend.api.user.entity.User;
import babfriend.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "식사 후기 API", description = "식사 후기 관련 API")
@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class EatReviewController {

    private final EatReviewService eatReviewService;
    private final UserService userService;
    private final BoardRepository boardRepository;

    @Operation(summary = "나에게 온 식사 리뷰들 API")
    @GetMapping
    public ResponseDto<EatReviewResponseDto> reviews(HttpServletRequest request, Pageable pageable) {
        User user = userService.findUser(request);

        return ResponseDto.success(eatReviewService.list(pageable, user));
    }

    @Operation(summary = "식사후기 작성 가능 여부 API")
    @GetMapping("/{boardId}/check")
    public ResponseDto<EatReviewPossibleDto> reviewsCheck(@PathVariable("boardId") Long boardId, HttpServletRequest request) {
        String msg = "리뷰 작성이 가능합니다.";
        boolean possible = true;

        User user = userService.findUser(request);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        Optional<EatReview> byUserAndBoard = eatReviewService.findByUserAndBoard(user, board);

        if (byUserAndBoard.isPresent()) {
            msg = "이미 리뷰를 작성하였습니다.";
            possible = false;
        }

        return ResponseDto.success(EatReviewPossibleDto.builder()
                        .msg(msg)
                        .possible(possible)
                        .build());
    }

    @Operation(summary = "식사후기 작성 가능 여부 API - 미완성")
    @PostMapping("/{boardId}")
    public ResponseDto reviewAdd(@PathVariable("boardId") Long boardId, HttpServletRequest request) {
        return null;
    }

}
