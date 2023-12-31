package babfriend.api.user.dto;

import babfriend.api.review.dto.EatReviewDto;
import babfriend.api.user.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDetailDto {

    private String nickName;
    private String profileImageUrl;
    private int temperature;
    private List<EatReviewDto> eatReviewDtos;


    public static UserDetailDto of(User user) {
        return null;
    }
}
