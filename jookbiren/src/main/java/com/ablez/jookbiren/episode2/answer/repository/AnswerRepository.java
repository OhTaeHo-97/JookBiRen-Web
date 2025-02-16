package com.ablez.jookbiren.episode2.answer.repository;

import static com.ablez.jookbiren.episode2.answer.entity.QAnswerEp02.answerEp02;
import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;

import com.ablez.jookbiren.episode2.answer.entity.AnswerEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepository extends Querydsl4RepositorySupport {
    public AnswerRepository() {
        super(AnswerEp02.class);
    }

    public Optional<AnswerEp02> findByQuiz(int placeCode, int quizNumber) {
        AnswerEp02 result = selectFrom(answerEp02)
                .innerJoin(answerEp02.quizEp02, quizEp02).fetchJoin()
                .where(
                        quizEp02.placeCode.eq(placeCode),
                        quizEp02.quizNumber.eq(quizNumber)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public Optional<AnswerEp02> findByQuizAndAnswer(int placeCode, int quizNumber, String answer) {
        AnswerEp02 result = selectFrom(answerEp02)
                .innerJoin(answerEp02.quizEp02, quizEp02).fetchJoin()
                .where(
                        quizEp02.placeCode.eq(placeCode),
                        quizEp02.quizNumber.eq(quizNumber),
                        answerEp02.answer.eq(answer)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
