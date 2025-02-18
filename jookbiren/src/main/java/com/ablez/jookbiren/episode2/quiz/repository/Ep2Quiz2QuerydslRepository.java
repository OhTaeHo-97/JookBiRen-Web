package com.ablez.jookbiren.episode2.quiz.repository;

import static com.ablez.jookbiren.episode2.quiz.entity.QQuiz2Ep02.quiz2Ep02;
import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;
import static com.ablez.jookbiren.episode2.user.entity.QUserEp02.userEp02;
import static com.ablez.jookbiren.userInfo.entity.QUserInfo.userInfo;

import com.ablez.jookbiren.episode2.quiz.entity.Quiz2Ep02;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep2Quiz2QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep2Quiz2QuerydslRepository() {
        super(Quiz2Ep02.class);
    }

    public Optional<Quiz2Ep02> findByQuizNumberAndUser2(int quizNumber, UserEp02 user) {
        Quiz2Ep02 result = selectFrom(quiz2Ep02)
                .innerJoin(quiz2Ep02.quiz, quizEp02)
                .where(
                        quizEp02.placeCode.eq(2),
                        quizEp02.quizNumber.eq(quizNumber),
                        quiz2Ep02.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<Quiz2Ep02> findAllQuiz2IsAnswer(UserEp02 user) {
        return selectFrom(quiz2Ep02)
                .where(
                        quiz2Ep02.userId.eq(user),
                        quiz2Ep02.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz2Ep02> findQuiz2IsAnswerByQuizNumber(int quizNumber, UserEp02 user) {
        Quiz2Ep02 result = selectFrom(quiz2Ep02)
                .where(
                        quiz2Ep02.userId.eq(user),
                        quiz2Ep02.quiz.quizNumber.eq(quizNumber),
                        quiz2Ep02.firstAnswerTime.isNotNull()
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<Quiz2Ep02> findAllQuiz2() {
        return selectFrom(quiz2Ep02)
                .innerJoin(quiz2Ep02.quiz, quizEp02).fetchJoin()
                .innerJoin(quiz2Ep02.userId, userEp02).fetchJoin()
                .leftJoin(userEp02.userInfo, userInfo).fetchJoin()
//                .leftJoin(userInfoEp02.orderInfo, orderInfo).fetchJoin()
//                .leftJoin(orderInfo.buyerInfo, buyerInfo).fetchJoin()
                .orderBy(quiz2Ep02.quiz.quizNumber.asc(), quiz2Ep02.quiz2Id.desc())
                .distinct()
                .fetch();
    }
}
