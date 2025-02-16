package com.ablez.jookbiren.episode1.quiz.repository;

import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.quiz.entity.QuizEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class QuizRepository extends Querydsl4RepositorySupport {
    public QuizRepository() {
        super(QuizEp01.class);
    }

    public Optional<QuizEp01> findQuiz(int placeCode, int quizNumber) {
        QuizEp01 quiz = selectFrom(quizEp01)
                .where(
                        quizEp01.placeCode.eq(placeCode),
                        quizEp01.quizNumber.eq(quizNumber)
                )
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
