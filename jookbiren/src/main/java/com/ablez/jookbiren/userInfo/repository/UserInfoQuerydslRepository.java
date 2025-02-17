package com.ablez.jookbiren.userInfo.repository;

import static com.ablez.jookbiren.episode1.user.entity.QUserEp01.userEp01;
import static com.ablez.jookbiren.episode2.user.entity.QUserEp02.userEp02;
import static com.ablez.jookbiren.security.entity.QAuthority.authority;
import static com.ablez.jookbiren.userInfo.entity.QUserInfo.userInfo;

import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import com.ablez.jookbiren.userInfo.entity.UserInfo;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoQuerydslRepository extends Querydsl4RepositorySupport {
    public UserInfoQuerydslRepository() {
        super(UserInfo.class);
    }

    public Optional<UserInfo> findById(long id) {
        UserInfo result = selectFrom(userInfo)
                .innerJoin(userInfo.authorities, authority).fetchJoin()
                .where(userInfo.userInfoId.eq(id))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public Optional<UserInfo> findByCodeEp1(String code) {
        UserInfo result = selectFrom(userInfo)
                .innerJoin(userInfo.userEp01, userEp01).fetchJoin()
                .where(userInfo.code.eq(code))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public Optional<UserInfo> findByCodeEp2(String code) {
        UserInfo result = selectFrom(userInfo)
                .innerJoin(userInfo.userEp02, userEp02).fetchJoin()
                .where(userInfo.code.eq(code))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
