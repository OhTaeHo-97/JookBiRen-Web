package com.ablez.jookbiren.episode2.quiz.repository;

import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;

import com.ablez.jookbiren.episode2.quiz.entity.QuizEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep2QuizRepository extends Querydsl4RepositorySupport {
    public Ep2QuizRepository() {
        super(QuizEp02.class);
    }

    public Optional<QuizEp02> findQuiz(int placeCode, int quizNumber) {
        QuizEp02 quiz = selectFrom(quizEp02)
                .where(
                        quizEp02.placeCode.eq(placeCode),
                        quizEp02.quizNumber.eq(quizNumber)
                )
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
