package babfriend.api.board.service;

import babfriend.api.board.dto.*;
import babfriend.api.board.entity.Board;
import babfriend.api.board.entity.BoardComment;
import babfriend.api.board.entity.BobMeeting;
import babfriend.api.board.repository.BoardCommentRepository;
import babfriend.api.board.repository.BoardRepository;
import babfriend.api.board.repository.BobMeetingRepository;
import babfriend.api.user.entity.User;
import babfriend.api.user.type.GenderType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BobMeetingRepository bobMeetingRepository;

    @Transactional
    public void postBoard(User user, BoardtDto boardDto) {
        Board board = boardRepository.save(boardDto.toEntity(user));

        BobMeeting bobMeeting = BobMeeting.builder()
                .board(board)
                .joinUser(user)
                .build();

        bobMeetingRepository.save(bobMeeting);
    }

    public BoardListResponseDto list(Pageable pageable, String search) {
        Page<Board> boardsAll = boardRepository.findByLocationContainingIgnoreCaseOrTitleContainingIgnoreCase(search, search, pageable);

        List<BoardsSimpleDto> boards = boardsAll.stream().map(BoardsSimpleDto::of)
                .collect(Collectors.toList());

        return BoardListResponseDto.builder()
                .boards(boards)
                .first(boardsAll.isFirst())
                .last(boardsAll.isLast())
                .empty(boardsAll.isEmpty())
                .totalElement(boardsAll.getTotalElements())
                .build();
    }

    public BoardResponseDto findById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        return BoardResponseDto.of(board);
    }

    @Transactional
    public void deleteById(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void update(Long boardId, BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        board.update(boardUpdateDto);
    }

    @Transactional
    public void updateFix(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        board.updateFix();
    }

    @Transactional
    public void createComment(Long boardId, User user, BoardCommentDto commentDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        BoardComment boardComment = commentDto.toEntity(user, board);

        boardCommentRepository.save(boardComment);
    }

    @Transactional
    public void joinOrCancel(User user, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        BobMeeting existingBobMeeting  = bobMeetingRepository.findByJoinUserAndBoard(user, board);

        if (existingBobMeeting != null) {
            bobMeetingRepository.delete(existingBobMeeting);
            board.joinOut();
            return;
        }
        if (board.getCurrentJoin() == board.getJoinLimit()) {
            throw new IllegalArgumentException("참여자가 가득 찬 게시글 입니다.");
        }

        BobMeeting bobMeeting = BobMeeting.builder()
                .joinUser(user)
                .board(board)
                .build();
        board.joinIn();
        bobMeetingRepository.save(bobMeeting);
    }

    public List<BoardsSimpleDto> getJoinMeeting(User user) {

        return boardRepository.joinBoardFindByUser(user);
    }

    public PossibilityDto possibilityCheck(User user, Long boardId) {

        String msg = "참여가 가능합니다.";
        boolean result = false;
        boolean alreadyJoin = false;

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        BobMeeting bobMeeting = bobMeetingRepository.findByJoinUserAndBoard(user, board);

        if (bobMeeting != null)  {
            msg = "참여한 식사입니다.";
            alreadyJoin = true;
            return new PossibilityDto(msg, result, alreadyJoin);
        }

        if (board.getEatTime().compareTo(LocalDateTime.now()) < 0) {
            msg = "식사 시간이 지났습니다.";
        } else if (board.getJoinLimit() <= board.getCurrentJoin()) {
            msg = "참여인원이 정원이 초과되어 있습니다.";
        } else if (board.isAgeGroupLimit() && Math.abs(board.getBabManager().getBirthYear() - 4) > 4 ) {
            msg = "나이 제한이 있는 식사입니다.";
        } else if (board.getGenderType() != GenderType.ALL && board.getGenderType() != user.getGenderType()) {
            msg = "성별 제한이 있는 식사입니다.";
        } else {
            result = true;
        }

        return new PossibilityDto(msg, result, alreadyJoin);
    }
}
