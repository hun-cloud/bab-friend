package babfriend.api.user.dto;

import babfriend.api.user.entity.User;
import babfriend.api.user.type.GenderType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class UserDto {
    private String email;
    private String name;
    private GenderType genderType;
    private int birthYear;
    private String profileImageUrl;

    @Builder
    private UserDto(String email, String name, GenderType genderType, int birthYear, String profileImageUrl) {
        this.email = email;
        this.name = name;
        this.genderType = genderType;
        this.birthYear = birthYear;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserDto of(OAuth2User oAuth2User) {
        return UserDto.builder()
                .name(oAuth2User.getAttribute("name"))
                .email(oAuth2User.getAttribute("email"))
                .genderType(oAuth2User.getAttribute("gender").equals("male") ? GenderType.MALE : GenderType.FEMALE)
                .birthYear(oAuth2User.getAttribute("birthYear"))
                .profileImageUrl(oAuth2User.getAttribute("profileImageUrl"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .genderType(genderType)
                .birthYear(birthYear)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
