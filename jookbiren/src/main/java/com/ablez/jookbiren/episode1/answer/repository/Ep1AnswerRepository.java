package com.ablez.jookbiren.episode1.answer.repository;

import static com.ablez.jookbiren.episode1.answer.entity.QAnswerEp01.answerEp01;
import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.answer.entity.AnswerEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep1AnswerRepository extends Querydsl4RepositorySupport {
    public Ep1AnswerRepository() {
        super(AnswerEp01.class);
    }

    public Optional<AnswerEp01> findByQuiz(int placeCode, int quizNumber) {
        AnswerEp01 result = select(answerEp01)
                .from(answerEp01)
                .join(answerEp01.quizEp01, quizEp01)
                .where(
                        quizEp01.placeCode.eq(placeCode),
                        quizEp01.quizNumber.eq(quizNumber)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public Optional<AnswerEp01> findByQuizAndAnswer(int placeCode, int quizNumber, String answer) {
        AnswerEp01 result = select(answerEp01)
                .from(answerEp01)
                .join(answerEp01.quizEp01, quizEp01)
                .where(
                        quizEp01.placeCode.eq(placeCode),
                        quizEp01.quizNumber.eq(quizNumber),
                        answerEp01.answer.eq(answer)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
