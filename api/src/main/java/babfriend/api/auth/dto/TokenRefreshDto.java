package babfriend.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Getter
@Setter
public class TokenRefreshDto {
    @Schema(description = "액세스 토큰", nullable = false)
    private String accessToken;
}
