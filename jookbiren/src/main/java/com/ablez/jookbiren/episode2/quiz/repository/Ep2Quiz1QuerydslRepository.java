package com.ablez.jookbiren.episode2.quiz.repository;

import static com.ablez.jookbiren.episode2.quiz.entity.QQuiz1Ep02.quiz1Ep02;
import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;
import static com.ablez.jookbiren.episode2.user.entity.QUserEp02.userEp02;
import static com.ablez.jookbiren.userInfo.entity.QUserInfo.userInfo;

import com.ablez.jookbiren.episode2.quiz.entity.Quiz1Ep02;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep2Quiz1QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep2Quiz1QuerydslRepository() {
        super(Quiz1Ep02.class);
    }

    public Optional<Quiz1Ep02> findByQuizNumberAndUser1(int quizNumber, UserEp02 user) {
        Quiz1Ep02 result = selectFrom(quiz1Ep02)
                .innerJoin(quiz1Ep02.quiz, quizEp02).fetchJoin()
                .where(
                        quizEp02.placeCode.eq(1),
                        quizEp02.quizNumber.eq(quizNumber),
                        quiz1Ep02.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<Quiz1Ep02> findAllQuiz1IsAnswer(UserEp02 user) {
        return selectFrom(quiz1Ep02)
                .where(
                        quiz1Ep02.userId.eq(user),
                        quiz1Ep02.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public List<Quiz1Ep02> findAllQuiz1() {
        return selectFrom(quiz1Ep02)
                .innerJoin(quiz1Ep02.quiz, quizEp02).fetchJoin()
                .innerJoin(quiz1Ep02.userId, userEp02).fetchJoin()
                .leftJoin(userEp02.userInfo, userInfo).fetchJoin()
//                .leftJoin(userInfoEp02.orderInfo, orderInfo).fetchJoin()
//                .leftJoin(orderInfo.buyerInfo, buyerInfo).fetchJoin()
                .orderBy(quiz1Ep02.quiz.quizNumber.asc(), quiz1Ep02.quiz1Id.desc())
                .distinct()
                .fetch();
    }
}
