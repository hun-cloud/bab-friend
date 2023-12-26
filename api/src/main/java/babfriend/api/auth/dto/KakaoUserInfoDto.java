package babfriend.api.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
public class KakaoUserInfoDto {

     private Map<String, Object> kakao_account;

}
