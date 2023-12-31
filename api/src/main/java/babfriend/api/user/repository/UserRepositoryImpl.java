package babfriend.api.user.repository;

import babfriend.api.review.entity.QEatReview;
import babfriend.api.user.dto.UserDetailDto;
import babfriend.api.user.entity.QUser;
import babfriend.api.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static babfriend.api.review.entity.QEatReview.eatReview;
import static babfriend.api.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public UserDetailDto findUserDetailByEmail(String email) {

        User findUser = queryFactory
                .selectFrom(user)
                .join(user.receivedReviews, eatReview).fetchJoin()
                .where(user.email.eq(email))
                .fetchOne();

        return UserDetailDto.of(findUser);
    }
}
