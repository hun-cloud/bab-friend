package babfriend.api.board.repository;

import babfriend.api.board.dto.BoardJoinMeetingDto;
import babfriend.api.board.dto.BoardsSimpleDto;
import babfriend.api.board.entity.BobMeeting;
import babfriend.api.board.entity.QBoard;
import babfriend.api.board.entity.QBobMeeting;
import babfriend.api.user.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static babfriend.api.board.entity.QBoard.board;
import static babfriend.api.board.entity.QBobMeeting.bobMeeting;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardsSimpleDto> joinBoardFindByUser(User user) {

        List<Tuple> result = queryFactory
                .select(bobMeeting, board)
                .from(bobMeeting)
                .join(bobMeeting.board, board).fetchJoin()
                .where(bobMeeting.joinUser.eq(user))
                .fetch();

        return result.stream()
                .map(BoardsSimpleDto::of)
                .toList();
    }
}
