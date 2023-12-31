package babfriend.api.user.repository;

import babfriend.api.user.dto.UserDetailDto;

public interface UserRepositoryCustom {

    UserDetailDto findUserDetailByEmail(String email);
}
