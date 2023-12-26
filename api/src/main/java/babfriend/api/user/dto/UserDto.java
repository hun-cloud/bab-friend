package babfriend.api.user.dto;

import babfriend.api.user.entity.User;
import babfriend.api.user.type.GenderType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

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

    public static UserDto of(Map<String, Object> kakaoUserInfo) {
        kakaoUserInfo = (Map<String, Object>) kakaoUserInfo.get("kakao_account");
        Map<String, Object> profile = (Map) kakaoUserInfo.get("profile");
        System.out.println("profile = " + profile);
        String profileImageUrl = (String) profile.get("profile_image_url");
        System.out.println(profileImageUrl);

        return UserDto.builder()
                .name((String) kakaoUserInfo.get("name"))
                .email((String) kakaoUserInfo.get("email"))
                .genderType("male".equals(kakaoUserInfo.get("gender")) ? GenderType.MALE : GenderType.FEMALE)
                .birthYear(Integer.valueOf((String) kakaoUserInfo.get("birthyear")))
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public User toEntity(String nickname) {
        return User.builder()
                .name(name)
                .email(email)
                .nickName(nickname)
                .genderType(genderType)
                .birthYear(birthYear)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
