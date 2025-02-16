package com.ablez.jookbiren.episode1.user.repository;

import static com.ablez.jookbiren.episode1.user.entity.QUserEp01.userEp01;

import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserQuerydslRepository extends Querydsl4RepositorySupport {
    public UserQuerydslRepository() {
        super(UserEp01.class);
    }

    public Optional<UserEp01> findById(long userId) {
        UserEp01 user = selectFrom(userEp01)
                .where(userEp01.userId.eq(userId))
                .fetchOne();
        return Optional.ofNullable(user);
    }

    public Optional<UserEp01> findByCode(String code) {
        UserEp01 user = selectFrom(userEp01)
                .where(userEp01.code.eq(code))
                .fetchOne();
        return Optional.ofNullable(user);
    }
}
