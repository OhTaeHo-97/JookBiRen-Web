package com.ablez.jookbiren.episode2.quiz.repository;

import static com.ablez.jookbiren.episode2.quiz.entity.QQuiz3Ep02.quiz3Ep02;
import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;
import static com.ablez.jookbiren.episode2.user.entity.QUserEp02.userEp02;
import static com.ablez.jookbiren.userInfo.entity.QUserInfo.userInfo;

import com.ablez.jookbiren.episode2.quiz.entity.Quiz3Ep02;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Quiz3QuerydslRepository extends Querydsl4RepositorySupport {
    public Quiz3QuerydslRepository() {
        super(Quiz3Ep02.class);
    }

    public Optional<Quiz3Ep02> findByQuizNumberAndUser3(int quizNumber, UserEp02 user) {
        Quiz3Ep02 result = selectFrom(quiz3Ep02)
                .innerJoin(quiz3Ep02.quiz, quizEp02)
                .where(
                        quizEp02.placeCode.eq(3),
                        quizEp02.quizNumber.eq(quizNumber),
                        quiz3Ep02.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<Quiz3Ep02> findAllQuiz3IsAnswer(UserEp02 user) {
        return selectFrom(quiz3Ep02)
                .where(
                        quiz3Ep02.userId.eq(user),
                        quiz3Ep02.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public List<Quiz3Ep02> findAllQuiz3() {
        return selectFrom(quiz3Ep02)
                .innerJoin(quiz3Ep02.quiz, quizEp02).fetchJoin()
                .innerJoin(quiz3Ep02.userId, userEp02).fetchJoin()
                .leftJoin(userEp02.userInfo, userInfo).fetchJoin()
//                .leftJoin(userInfoEp02.orderInfo, orderInfo).fetchJoin()
//                .leftJoin(orderInfo.buyerInfo, buyerInfo).fetchJoin()
                .orderBy(quiz3Ep02.quiz.quizNumber.asc(), quiz3Ep02.quiz3Id.desc())
                .distinct()
                .fetch();
    }
}
