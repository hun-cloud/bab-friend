package babfriend.api.user.service;

import babfriend.api.auth.TokenProvider;
import babfriend.api.common.ResponseDto;
import babfriend.api.common.service.RandomNicknameService;
import babfriend.api.user.dto.UserDetailDto;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.entity.User;
import babfriend.api.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RandomNicknameService randomNicknameService;
    private final TokenProvider tokenProvider;

    // 회원가입
    public void join(UserDto userDto) {

        Optional<User> userOpt = userRepository.findByEmail(userDto.getEmail());

        if (userOpt.isEmpty()) {
            String nickname = randomNicknameService.createRandomNickname();
            User user = userDto.toEntity(nickname);

            userRepository.save(user);
        }
    }

    public UserDto userInfo(HttpServletRequest request) {
        String email = getEmail(request);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException());

        return UserDto.of(user);
    }

    public UserDetailDto userDetailInfo(HttpServletRequest request) {
        String email = getEmail(request);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException());

        return UserDetailDto.of(user);
    }

    private String getEmail(HttpServletRequest request) {
        String accessToken = tokenProvider.resolveAccessToken(request);
        Claims claims = tokenProvider.parseClaims(accessToken);
        String email = claims.getSubject();
        return email;
    }
}
