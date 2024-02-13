package babfriend.api.board.repository;

import babfriend.api.board.entity.Board;
import babfriend.api.board.entity.BobMeeting;
import babfriend.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BobMeetingRepository extends JpaRepository<BobMeeting, Long> {

    BobMeeting findByJoinUserAndBoard(User user, Board board);
}
