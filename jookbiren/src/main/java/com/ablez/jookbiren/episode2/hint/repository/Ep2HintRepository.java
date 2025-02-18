package com.ablez.jookbiren.episode2.hint.repository;

import static com.ablez.jookbiren.episode2.hint.entity.QHintEp02.hintEp02;
import static com.ablez.jookbiren.episode2.quiz.entity.QQuizEp02.quizEp02;

import com.ablez.jookbiren.episode2.hint.entity.HintEp02;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep2HintRepository extends Querydsl4RepositorySupport {
    public Ep2HintRepository() {
        super(HintEp02.class);
    }

    public List<HintEp02> findAllByQuiz(int placeCode, int quizNumber) {
        return selectFrom(hintEp02)
                .innerJoin(hintEp02.quiz, quizEp02).fetchJoin()
                .where(
                        hintEp02.quiz.placeCode.eq(placeCode),
                        hintEp02.quiz.quizNumber.eq(quizNumber)
                )
                .distinct()
                .fetch();
    }

    public Optional<HintEp02> findByQuizAndHintNumber(int placeCode, int quizNumber, int hintNumber) {
        HintEp02 result = selectFrom(hintEp02)
                .innerJoin(hintEp02.quiz, quizEp02).fetchJoin()
                .where(
                        hintEp02.quiz.placeCode.eq(placeCode),
                        hintEp02.quiz.quizNumber.eq(quizNumber),
                        hintEp02.hintOrder.eq(hintNumber)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
