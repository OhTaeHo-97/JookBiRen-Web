package com.ablez.jookbiren.episode1.quiz.repository;

import static com.ablez.jookbiren.episode1.quiz.entity.QQuiz1Ep01.quiz1Ep01;
import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz1Ep01;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Quiz1QuerydslRepository extends Querydsl4RepositorySupport {
    public Quiz1QuerydslRepository() {
        super(Quiz1Ep01.class);
    }

    public List<Quiz1Ep01> findAllQuiz1(UserEp01 user) {
        return selectFrom(quiz1Ep01)
                .where(quiz1Ep01.userId.eq(user))
                .fetch();
    }

    public List<Quiz1Ep01> findAllQuiz1IsAnswer(UserEp01 user) {
        return selectFrom(quiz1Ep01)
                .where(
                        quiz1Ep01.userId.eq(user),
                        quiz1Ep01.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz1Ep01> findByQuizNumberAndUser1(int quizNumber, UserEp01 user) {
        Quiz1Ep01 quiz = selectFrom(quiz1Ep01)
                .join(quiz1Ep01.quiz, quizEp01)
                .where(
                        quizEp01.placeCode.eq(1),
                        quizEp01.quizNumber.eq(quizNumber),
                        quiz1Ep01.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
