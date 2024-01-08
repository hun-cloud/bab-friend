package babfriend.api.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserUpdateDto {
    private String nickName;
    private MultipartFile profileImage;
}
