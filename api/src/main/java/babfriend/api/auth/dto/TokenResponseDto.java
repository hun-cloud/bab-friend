package babfriend.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenResponseDto {

    @Schema(description = "액세스 토큰", nullable = false)
    private final String accessToken;
}
