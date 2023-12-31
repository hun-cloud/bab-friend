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
                .email("aaa@naver.com")
                .name("aaa")
                .genderType(GenderType.FEMALE)
                .build();
        userRepository.save(user);
    }


    @Test
    void findUserDetail() {
        userRepository.findUserDetailByEmail("jhl9838@naver.com");
    }
}