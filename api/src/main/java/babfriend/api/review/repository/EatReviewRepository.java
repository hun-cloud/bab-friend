package babfriend.api.review.repository;

import babfriend.api.board.entity.Board;
import babfriend.api.review.entity.EatReview;
import babfriend.api.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EatReviewRepository extends JpaRepository<EatReview, Long> {

    Page<EatReview> findByReviewee(User user, Pageable pageable);

    Optional<EatReview> findByRevieweeAndBoard(User user, Board board);
}
