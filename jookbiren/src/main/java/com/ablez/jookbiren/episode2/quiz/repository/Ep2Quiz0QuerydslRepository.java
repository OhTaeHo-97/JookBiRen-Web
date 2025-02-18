package com.ablez.jookbiren.episode2.quiz.repository;

import static com.ablez.jookbiren.episode2.quiz.entity.QQuiz0Ep02.quiz0Ep02;
import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;
import static com.ablez.jookbiren.episode2.user.entity.QUserEp02.userEp02;
import static com.ablez.jookbiren.userInfo.entity.QUserInfo.userInfo;

import com.ablez.jookbiren.episode2.quiz.entity.Quiz0Ep02;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep2Quiz0QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep2Quiz0QuerydslRepository() {
        super(Quiz0Ep02.class);
    }

    public Optional<Quiz0Ep02> findByQuizNumberAndUser0(int quizNumber, UserEp02 user) {
        Quiz0Ep02 result = selectFrom(quiz0Ep02)
                .innerJoin(quiz0Ep02.quiz, quizEp02).fetchJoin()
                .where(
                        quizEp02.placeCode.eq(0),
                        quizEp02.quizNumber.eq(quizNumber),
                        quiz0Ep02.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public List<Quiz0Ep02> findAllQuiz0IsAnswer(UserEp02 user) {
        return selectFrom(quiz0Ep02)
                .where(
                        quiz0Ep02.userId.eq(user),
                        quiz0Ep02.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public List<Quiz0Ep02> findAllQuiz0() {
        return selectFrom(quiz0Ep02)
                .innerJoin(quiz0Ep02.quiz, quizEp02).fetchJoin()
                .innerJoin(quiz0Ep02.userId, userEp02).fetchJoin()
                .leftJoin(userEp02.userInfo, userInfo).fetchJoin()
//                .leftJoin(userInfo.orderInfo, orderInfo).fetchJoin()
//                .leftJoin(orderInfo.buyerInfo, buyerInfo).fetchJoin()
                .orderBy(quiz0Ep02.quiz.quizNumber.asc(), quiz0Ep02.quiz0Id.desc())
                .distinct()
                .fetch();
    }
}
