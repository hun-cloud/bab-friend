package babfriend.api.user.service;

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

    // 회원가입
    public void join(UserDto userDto) {
        User user = userDto.toEntity();
        userRepository.save(user);
    }
}
