package com.ablez.jookbiren.episode3.user.repository;

import static com.ablez.jookbiren.episode3.user.entity.QUserEp03.userEp03;

import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserQuerydslRepository extends Querydsl4RepositorySupport {
    public UserQuerydslRepository() {
        super(UserEp03.class);
    }

    public Optional<UserEp03> findById(long userId) {
        UserEp03 user = selectFrom(userEp03)
                .where(userEp03.userId.eq(userId))
                .fetchOne();
        return Optional.ofNullable(user);
    }

    public Optional<UserEp03> findByCode(String code) {
        UserEp03 user = selectFrom(userEp03)
                .where(userEp03.code.eq(code))
                .fetchOne();
        return Optional.ofNullable(user);
    }
}
