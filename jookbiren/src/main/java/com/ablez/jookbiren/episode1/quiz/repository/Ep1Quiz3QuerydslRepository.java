package com.ablez.jookbiren.episode1.quiz.repository;

import static com.ablez.jookbiren.episode1.quiz.entity.QQuiz3Ep01.quiz3Ep01;
import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz3Ep01;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep1Quiz3QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep1Quiz3QuerydslRepository() {
        super(Quiz3Ep01.class);
    }

    public List<Quiz3Ep01> findAllQuiz3(UserEp01 user) {
        return selectFrom(quiz3Ep01)
                .where(quiz3Ep01.userId.eq(user))
                .fetch();
    }

    public List<Quiz3Ep01> findAllQuiz3IsAnswer(UserEp01 user) {
        return selectFrom(quiz3Ep01)
                .where(
                        quiz3Ep01.userId.eq(user),
                        quiz3Ep01.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz3Ep01> findByQuizNumberAndUser3(int quizNumber, UserEp01 user) {
        Quiz3Ep01 quiz = selectFrom(quiz3Ep01)
                .join(quiz3Ep01.quiz, quizEp01)
                .where(
                        quizEp01.placeCode.eq(3),
                        quizEp01.quizNumber.eq(quizNumber),
                        quiz3Ep01.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
