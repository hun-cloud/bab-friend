package babfriend.api.review.dto;

import babfriend.api.common.service.FileUtils;
import babfriend.api.review.entity.EatReview;
import babfriend.api.review.type.ReviewStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class EatReviewDto {

    private Long id;
    private String writer;
    private String content;
    private String writerImageUrl;
    private LocalDateTime createdAt;
    private ReviewStatus reviewStatus;

    @Builder
    private EatReviewDto(Long id, String writer, String content, String writerImageUrl, LocalDateTime createdAt, ReviewStatus reviewStatus) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.writerImageUrl = writerImageUrl;
        this.createdAt = createdAt;
        this.reviewStatus = reviewStatus;
    }

    public static List<EatReviewDto> ofList(List<EatReview> reviewees) {
        return reviewees.stream().map(EatReviewDto::of)
                .toList();
    }

    public static EatReviewDto of (EatReview eatReview) {

        String profileImageUrl = eatReview.getReviewer().getProfileImageUrl();

        if (profileImageUrl != null && !profileImageUrl.startsWith("http")) {
            profileImageUrl = FileUtils.url + "/image/" + profileImageUrl;
        }

        return EatReviewDto.builder()
                .id(eatReview.getId())
                .writer(eatReview.getReviewer().getNickName())
                .writerImageUrl(profileImageUrl)
                .content(eatReview.getContent())
                .createdAt(eatReview.getCreatedAt())
                .reviewStatus(ReviewStatus.DISABLED) // mark
                .build();
    }
}
