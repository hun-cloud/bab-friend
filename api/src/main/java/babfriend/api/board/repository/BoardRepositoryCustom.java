package babfriend.api.board.repository;

import babfriend.api.board.dto.BoardJoinMeetingDto;
import babfriend.api.board.dto.BoardsSimpleDto;
import babfriend.api.user.entity.User;

import java.util.List;

public interface BoardRepositoryCustom {

    List<BoardsSimpleDto> joinBoardFindByUser(User user);
}
