package com.ablez.jookbiren.episode1.quiz.repository;

import static com.ablez.jookbiren.episode1.quiz.entity.QQuiz2Ep01.quiz2Ep01;
import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz2Ep01;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep1Quiz2QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep1Quiz2QuerydslRepository() {
        super(Quiz2Ep01.class);
    }

    public List<Quiz2Ep01> findAllQuiz2(UserEp01 user) {
        return selectFrom(quiz2Ep01)
                .where(quiz2Ep01.userId.eq(user))
                .fetch();
    }

    public List<Quiz2Ep01> findAllQuiz2IsAnswer(UserEp01 user) {
        return selectFrom(quiz2Ep01)
                .where(
                        quiz2Ep01.userId.eq(user),
                        quiz2Ep01.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz2Ep01> findByQuizNumberAndUser2(int quizNumber, UserEp01 user) {
        Quiz2Ep01 quiz = selectFrom(quiz2Ep01)
                .join(quiz2Ep01.quiz, quizEp01)
                .where(
                        quizEp01.placeCode.eq(2),
                        quizEp01.quizNumber.eq(quizNumber),
                        quiz2Ep01.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
