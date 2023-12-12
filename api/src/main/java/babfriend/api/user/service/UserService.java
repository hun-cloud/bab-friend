package babfriend.api.user.service;

import babfriend.api.user.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
