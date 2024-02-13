package babfriend.api.review.service;

import babfriend.api.board.entity.Board;
import babfriend.api.review.dto.EatReviewDto;
import babfriend.api.review.entity.EatReview;
import babfriend.api.review.repository.EatReviewRepository;
import babfriend.api.user.dto.EatReviewResponseDto;
import babfriend.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EatReviewService {

    private final EatReviewRepository eatReviewRepository;

    public EatReviewResponseDto list(Pageable pageable, User user) {
        Page<EatReview> reviewee = eatReviewRepository.findByReviewee(user, pageable);

        List<EatReviewDto> eatReviewDtos = reviewee.stream()
                .map(EatReviewDto::of)
                .toList();

        return EatReviewResponseDto.builder()
                .reviews(eatReviewDtos)
                .first(reviewee.isFirst())
                .last(reviewee.isLast())
                .empty(reviewee.isEmpty())
                .totalElement(reviewee.getTotalElements())
                .build();
    }

    public Optional<EatReview> findByUserAndBoard(User user, Board board) {
        return eatReviewRepository.findByRevieweeAndBoard(user, board);
    }
}
