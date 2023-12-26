package babfriend.api.user.service;

import babfriend.api.common.service.RandomNicknameService;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.entity.User;
import babfriend.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RandomNicknameService randomNicknameService;

    // 회원가입
    public void join(UserDto userDto) {

        Optional<User> userOpt = userRepository.findByEmail(userDto.getEmail());

        if (userOpt.isEmpty()) {
            String nickname = randomNicknameService.createRandomNickname();
            User user = userDto.toEntity(nickname);

            userRepository.save(user);
        }
    }
}
