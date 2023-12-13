package babfriend.api.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class OAuth2Attribute {
    private Map<String, Object> attributes; // 사용자 속성 정보를 담는 Map
    private String attributeKey; // 사용자 속성의 키 값
    private String email; // 이메일 정보
    private String name; // 이름 정보
    private String picture; // 프로필 사진 정보
    private String provider; // 제공자 정보
    private String oAuthId; // oAuthId
    private Integer birthYear; // 태어난 년도
    private String gender; // 성별

    // 서비스에 따라 OAuth2Attribute 객체를 생성하는 메서드
    static OAuth2Attribute of(String provider, String attributeKey,
                              Map<String, Object> attributes) {
        return switch (provider) {
            case "google" -> ofGoogle(provider, attributeKey, attributes);
            case "kakao" -> ofKakao(provider, "email", attributes);
            case "naver" -> ofNaver(provider, "id", attributes);
            default -> throw new RuntimeException();
        };
    }

    /*
     *   Google 로그인일 경우 사용하는 메서드, 사용자 정보가 따로 Wrapping 되지 않고 제공되어,
     *   바로 get() 메서드로 접근이 가능하다.
     * */
    private static OAuth2Attribute ofGoogle(String provider, String attributeKey,
                                            Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .email((String) attributes.get("email"))
                .provider(provider)
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    /*
     *   Kakao 로그인일 경우 사용하는 메서드, 필요한 사용자 정보가 kakaoAccount -> kakaoProfile 두번 감싸져 있어서,
     *   두번 get() 메서드를 이용해 사용자 정보를 담고있는 Map을 꺼내야한다.
     * */
    private static OAuth2Attribute ofKakao(String provider, String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .oAuthId(attributes.get("id").toString())
                .email((String) kakaoAccount.get("email"))
                .name((String) kakaoAccount.get("name"))
                .birthYear(Integer.valueOf((String) kakaoAccount.get("birthyear")))
                .gender((String) kakaoAccount.get("gender"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .provider(provider)
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    /*
     *  Naver 로그인일 경우 사용하는 메서드, 필요한 사용자 정보가 response Map에 감싸져 있어서,
     *  한번 get() 메서드를 이용해 사용자 정보를 담고있는 Map을 꺼내야한다.
     * */
    private static OAuth2Attribute ofNaver(String provider, String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .email((String) response.get("email"))
                .attributes(response)
                .provider(provider)
                .attributeKey(attributeKey)
                .build();
    }


    // OAuth2User 객체에 넣어주기 위해서 Map으로 값들을 반환해준다.
    Map<String, Object> convertToMap() {
        return switch (provider) {
            case "google" -> convertToGoogleMap();
            case "kakao" -> convertToKakaoMap();
            case "naver" -> convertToNaverMap();
            default -> throw new RuntimeException();
        };

    }

    private Map<String, Object> convertToKakaoMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", oAuthId);
        map.put("email", email);
        map.put("name", name);
        map.put("gender", gender);
        map.put("birthYear", birthYear);
        map.put("picture", picture);
        map.put("key", attributeKey); // email
        map.put("provider", provider); // kakao
        return map;
    }

    private Map<String, Object> convertToNaverMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);
        map.put("provider", provider);
        return map;
    }

    private Map<String, Object> convertToGoogleMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);
        map.put("provider", provider);
        return map;
    }
}
