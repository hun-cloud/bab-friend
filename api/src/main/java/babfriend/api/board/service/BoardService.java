package babfriend.api.board.service;

import babfriend.api.board.dto.*;
import babfriend.api.board.entity.Board;
import babfriend.api.board.repository.BoardRepository;
import babfriend.api.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void postBoard(User user, BoardtDto boardDto) {
        boardRepository.save(boardDto.toEntity(user));
    }

    public BoardListResponseDto list(Pageable pageable) {
        Page<Board> boardsAll = boardRepository.findAll(pageable);

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
}
