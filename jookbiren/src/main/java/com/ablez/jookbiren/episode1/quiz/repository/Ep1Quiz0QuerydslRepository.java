package com.ablez.jookbiren.episode1.quiz.repository;

import static com.ablez.jookbiren.episode1.quiz.entity.QQuiz0Ep01.quiz0Ep01;
import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz0Ep01;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep1Quiz0QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep1Quiz0QuerydslRepository() {
        super(Quiz0Ep01.class);
    }

    public List<Quiz0Ep01> findAllQuiz0(UserEp01 user) {
        return selectFrom(quiz0Ep01)
                .where(quiz0Ep01.userId.eq(user))
                .fetch();
    }

    public List<Quiz0Ep01> findAllQuiz0IsAnswer(UserEp01 user) {
        return selectFrom(quiz0Ep01)
                .where(
                        quiz0Ep01.userId.eq(user),
                        quiz0Ep01.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz0Ep01> findByQuizNumberAndUser0(int quizNumber, UserEp01 user) {
        Quiz0Ep01 quiz = selectFrom(quiz0Ep01)
                .join(quiz0Ep01.quiz, quizEp01)
                .where(
                        quizEp01.placeCode.eq(0),
                        quizEp01.quizNumber.eq(quizNumber),
                        quiz0Ep01.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
