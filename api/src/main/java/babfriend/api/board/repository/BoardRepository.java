package babfriend.api.board.repository;

import babfriend.api.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Page<Board> findByLocationContainingIgnoreCaseOrTitleContainingIgnoreCase(String search1, String search2, Pageable pageable);
}
