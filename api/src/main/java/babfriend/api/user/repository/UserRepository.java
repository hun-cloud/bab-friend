package babfriend.api.user.repository;

import babfriend.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryCustom{
    Optional<User> findByEmail(String email);
}
