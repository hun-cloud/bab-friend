package babfriend.api.board.dto;

import babfriend.api.board.type.CategoryType;
import babfriend.api.user.type.GenderType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class BoardUpdateDto {

    private String title;
    private String content;
    private String location;
    private CategoryType categoryType;
    private LocalDateTime eatTime;
    private boolean alcohol;
    private int joinLimit;
    private boolean ageGroupLimit;
    private GenderType genderType;
    private String linkUrl;
    private int priceRange;
}
