package babfriend.api.user.repository;

import babfriend.api.user.entity.User;
import babfriend.api.user.type.GenderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@Commit
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userSave() {
        User user = User.builder()
                .oauthId("aaa@naver.com")
                .name("aaa")
                .age(20)
                .genderType(GenderType.FEMALE)
                .temperature(36)
                .build();
        userRepository.save(user);
    }
}