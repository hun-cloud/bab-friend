package babfriend.api.board.dto;

import babfriend.api.board.entity.Board;
import babfriend.api.board.type.CategoryType;
import babfriend.api.user.entity.User;
import babfriend.api.user.type.GenderType;
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
    private String location;
    private int priceRange;
    private boolean alcohol;
    private boolean ageGroupLimit;
    private CategoryType categoryType;
    private GenderType genderType;

    @Builder
    private BoardtDto(String title, String content, String linkUrl, LocalDateTime eatTime, int joinLimit, boolean fix, String location, int priceRange, boolean alcohol, boolean ageGroupLimit, CategoryType categoryType, GenderType genderType) {
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
        this.eatTime = eatTime;
        this.joinLimit = joinLimit;
        this.fix = fix;
        this.location = location;
        this.priceRange = priceRange;
        this.alcohol = alcohol;
        this.ageGroupLimit = ageGroupLimit;
        this.categoryType = categoryType;
        this.genderType = genderType;
    }

    public Board toEntity(User user) {

        return Board.builder()
                .babManager(user)
                .title(title)
                .content(content)
                .linkUrl(linkUrl)
                .eatTime(eatTime)
                .joinLimit(joinLimit)
                .location(location)
                .fix(fix)
                .genderType(genderType)
                .priceRange(priceRange)
                .alcohol(alcohol)
                .ageGroupLimit(ageGroupLimit)
                .categoryType(categoryType)
                .build();
    }
}
