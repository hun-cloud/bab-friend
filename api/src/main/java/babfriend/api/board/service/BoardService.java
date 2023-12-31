package babfriend.api.board.service;

import babfriend.api.board.dto.BoardtDto;
import babfriend.api.board.repository.BoardRepository;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void postBoard(User user, BoardtDto boardDto) {
        boardRepository.save(boardDto.toEntity(user));
    }
}
