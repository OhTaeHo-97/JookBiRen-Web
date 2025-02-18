package com.ablez.jookbiren.episode3.quiz.repository;

import static com.ablez.jookbiren.episode3.quiz.entity.QQuiz1Ep03.quiz1Ep03;
import static com.ablez.jookbiren.episode3.quiz.entity.QQuizEp03.quizEp03;

import com.ablez.jookbiren.episode3.quiz.entity.Quiz1Ep03;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep3Quiz1QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep3Quiz1QuerydslRepository() {
        super(Quiz1Ep03.class);
    }

    public List<Quiz1Ep03> findAllQuiz1(UserEp03 user) {
        return selectFrom(quiz1Ep03)
                .where(quiz1Ep03.userId.eq(user))
                .fetch();
    }

    public List<Quiz1Ep03> findAllQuiz1IsAnswer(UserEp03 user) {
        return selectFrom(quiz1Ep03)
                .where(
                        quiz1Ep03.userId.eq(user),
                        quiz1Ep03.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz1Ep03> findByQuizNumberAndUser1(int quizNumber, UserEp03 user) {
        Quiz1Ep03 quiz = selectFrom(quiz1Ep03)
                .join(quiz1Ep03.quiz, quizEp03)
                .where(
                        quizEp03.placeCode.eq(1),
                        quizEp03.quizNumber.eq(quizNumber),
                        quiz1Ep03.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
