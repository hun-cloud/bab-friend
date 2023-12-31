package babfriend.api.board.dto;

import babfriend.api.board.entity.Board;
import babfriend.api.board.type.CategoryType;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardtDto {

    private String title;
    private String content;
    private String linkUrl;
    private LocalDateTime eatTime;
    private int joinLimit;
    private boolean fix;
    private int priceRange;
    private boolean alcohol;
    private boolean ageGroupLimit;
    private CategoryType categoryType;

    @Builder
    private BoardtDto(String title, String content, String linkUrl, LocalDateTime eatTime, int joinLimit, boolean fix, int priceRange, boolean alcohol, boolean ageGroupLimit, CategoryType categoryType) {
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
        this.eatTime = eatTime;
        this.joinLimit = joinLimit;
        this.fix = fix;
        this.priceRange = priceRange;
        this.alcohol = alcohol;
        this.ageGroupLimit = ageGroupLimit;
        this.categoryType = categoryType;
    }

    public Board toEntity(User user) {

        return Board.builder()
                .babManager(user)
                .title(title)
                .content(content)
                .linkUrl(linkUrl)
                .eatTime(eatTime)
                .joinLimit(joinLimit)
                .fix(fix)
                .priceRange(priceRange)
                .alcohol(alcohol)
                .ageGroupLimit(ageGroupLimit)
                .categoryType(categoryType)
                .build();
    }
}
