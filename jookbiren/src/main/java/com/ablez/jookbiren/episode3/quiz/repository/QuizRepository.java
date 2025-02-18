package com.ablez.jookbiren.episode3.quiz.repository;

import static com.ablez.jookbiren.episode3.quiz.entity.QQuizEp03.quizEp03;

import com.ablez.jookbiren.episode3.quiz.entity.QuizEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class QuizRepository extends Querydsl4RepositorySupport {
    public QuizRepository() {
        super(QuizEp03.class);
    }

    public Optional<QuizEp03> findQuiz(int placeCode, int quizNumber) {
        QuizEp03 quiz = selectFrom(quizEp03)
                .where(
                        quizEp03.placeCode.eq(placeCode),
                        quizEp03.quizNumber.eq(quizNumber)
                )
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
