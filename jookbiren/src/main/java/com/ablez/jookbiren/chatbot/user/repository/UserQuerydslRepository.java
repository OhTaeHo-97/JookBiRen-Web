package com.ablez.jookbiren.chatbot.user.repository;

import static com.ablez.jookbiren.chatbot.user.entity.QUserEp00.userEp00;

import com.ablez.jookbiren.chatbot.user.entity.UserEp00;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserQuerydslRepository extends Querydsl4RepositorySupport {
    public UserQuerydslRepository() {
        super(UserEp00.class);
    }

    public Optional<UserEp00> findByCode(String code) {
        UserEp00 result = selectFrom(userEp00)
                .where(userEp00.code.eq(code))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<UserEp00> findAllByFirstId(String kakaoId) {
        List<UserEp00> userEp00s = selectFrom(userEp00)
                .where(userEp00.firstId.eq(kakaoId))
                .fetch();

        if (userEp00s == null || userEp00s.isEmpty()) {
            return null;
        }
        return userEp00s;
    }

    public List<UserEp00> findAllByFirstIdOrderByCustomDesc(String kakaoId) {
        List<UserEp00> userEp00s = selectFrom(userEp00)
                .where(userEp00.firstId.eq(kakaoId))
                .orderBy(userEp00.custom.desc())
                .fetch();

        if (userEp00s == null || userEp00s.isEmpty()) {
            return null;
        }
        return userEp00s;
    }
}
