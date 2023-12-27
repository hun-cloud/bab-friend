package babfriend.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {
    @Schema(description = "액세스 토큰", nullable = false)
    private String accessToken;
    @Schema(description = "리프레시 토큰", nullable = false)
    private String refreshToken;


    @Builder
    private TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
