package babfriend.api.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EatReviewPossibleDto {

    private boolean possible;
    private String msg;

    @Builder
    private EatReviewPossibleDto(boolean possible, String msg) {
        this.possible = possible;
        this.msg = msg;
    }
}
