package com.ablez.jookbiren.episode2.user.repository;

import static com.ablez.jookbiren.episode2.user.entity.QUserEp02.userEp02;
import static com.ablez.jookbiren.userInfo.entity.QUserInfo.userInfo;

import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class Ep2UserQuerydslRepository extends Querydsl4RepositorySupport {
    public Ep2UserQuerydslRepository() {
        super(UserEp02.class);
    }

    public List<UserEp02> findAll() {
        return selectFrom(userEp02)
                .innerJoin(userEp02.userInfo, userInfo).fetchJoin()
//                .leftJoin(userInfoEp02.orderInfo, orderInfo).fetchJoin()
//                .leftJoin(orderInfo.buyerInfo, buyerInfo).fetchJoin()
                .orderBy(userEp02.userId.desc())
                .distinct()
                .fetch();
    }
}
