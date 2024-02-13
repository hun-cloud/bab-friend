package babfriend.api.user.dto;

import babfriend.api.review.dto.EatReviewDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EatReviewResponseDto {

    private List<EatReviewDto> reviews;
    private boolean first;
    private boolean last;
    private Long totalElement;
    private boolean empty;

    @Builder
    private EatReviewResponseDto(List<EatReviewDto> reviews, boolean first, boolean last, Long totalElement, boolean empty) {
        this.reviews = reviews;
        this.first = first;
        this.last = last;
        this.totalElement = totalElement;
        this.empty = empty;
    }
}
