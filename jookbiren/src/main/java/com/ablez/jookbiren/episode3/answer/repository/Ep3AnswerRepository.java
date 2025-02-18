package com.ablez.jookbiren.episode3.answer.repository;

import static com.ablez.jookbiren.episode3.answer.entity.QAnswerEp03.answerEp03;
import static com.ablez.jookbiren.episode3.quiz.entity.QQuizEp03.quizEp03;

import com.ablez.jookbiren.episode3.answer.entity.AnswerEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep3AnswerRepository extends Querydsl4RepositorySupport {
    public Ep3AnswerRepository() {
        super(AnswerEp03.class);
    }

    public Optional<AnswerEp03> findByQuiz(int placeCode, int quizNumber) {
        AnswerEp03 result = select(answerEp03)
                .from(answerEp03)
                .join(answerEp03.quizEp03, quizEp03)
                .where(
                        quizEp03.placeCode.eq(placeCode),
                        quizEp03.quizNumber.eq(quizNumber)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public Optional<AnswerEp03> findByQuizAndAnswer(int placeCode, int quizNumber, String answer) {
        AnswerEp03 result = select(answerEp03)
                .from(answerEp03)
                .join(answerEp03.quizEp03, quizEp03)
                .where(
                        quizEp03.placeCode.eq(placeCode),
                        quizEp03.quizNumber.eq(quizNumber),
                        answerEp03.answer.eq(answer)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
