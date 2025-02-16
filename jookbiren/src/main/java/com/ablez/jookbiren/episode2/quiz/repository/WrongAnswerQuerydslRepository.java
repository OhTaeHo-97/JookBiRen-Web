package com.ablez.jookbiren.episode2.quiz.repository;

import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;
import static com.ablez.jookbiren.episode2.quiz.entity.QWrongAnswerEp02.wrongAnswerEp02;
import static com.ablez.jookbiren.episode2.user.entity.QUserEp02.userEp02;
import static com.ablez.jookbiren.userInfo.entity.QUserInfo.userInfo;

import com.ablez.jookbiren.episode2.quiz.entity.WrongAnswerEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class WrongAnswerQuerydslRepository extends Querydsl4RepositorySupport {
    public WrongAnswerQuerydslRepository() {
        super(WrongAnswerEp02.class);
    }

    public List<WrongAnswerEp02> findAll() {
        return selectFrom(wrongAnswerEp02)
                .innerJoin(wrongAnswerEp02.quiz, quizEp02).fetchJoin()
                .innerJoin(wrongAnswerEp02.user, userEp02).fetchJoin()
                .innerJoin(userEp02.userInfo, userInfo).fetchJoin()
//                .leftJoin(userInfoEp02.orderInfo, orderInfo).fetchJoin()
//                .leftJoin(orderInfo.buyerInfo, buyerInfo).fetchJoin()
                .orderBy(wrongAnswerEp02.quiz.placeCode.asc(), wrongAnswerEp02.quiz.quizNumber.asc(),
                        wrongAnswerEp02.time.desc())
                .distinct()
                .fetch();
    }
}
