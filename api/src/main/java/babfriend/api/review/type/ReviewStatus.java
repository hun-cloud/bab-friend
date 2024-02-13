package babfriend.api.review.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReviewStatus {
    ENABLED("가능합니다."),
    DISABLED("불가능합니다."),
    DONE("이미 했습니다.");

    private final String text;
}
