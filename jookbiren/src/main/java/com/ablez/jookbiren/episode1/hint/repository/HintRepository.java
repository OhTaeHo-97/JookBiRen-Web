package com.ablez.jookbiren.episode1.hint.repository;

import static com.ablez.jookbiren.episode1.hint.entity.QHintEp01.hintEp01;
import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.hint.entity.HintEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class HintRepository extends Querydsl4RepositorySupport {
    public HintRepository() {
        super(HintEp01.class);
    }

    public List<HintEp01> findAllByQuiz(int placeCode, int quizNumber) {
        return selectFrom(hintEp01)
                .innerJoin(hintEp01.quiz, quizEp01).fetchJoin()
                .where(
                        hintEp01.quiz.placeCode.eq(placeCode),
                        hintEp01.quiz.quizNumber.eq(quizNumber)
                )
                .distinct()
                .fetch();
    }

    public Optional<HintEp01> findByQuizAndHintNumber(int placeCode, int quizNumber, int hintNumber) {
        HintEp01 result = selectFrom(hintEp01)
                .innerJoin(hintEp01.quiz, quizEp01).fetchJoin()
                .where(
                        hintEp01.quiz.placeCode.eq(placeCode),
                        hintEp01.quiz.quizNumber.eq(quizNumber),
                        hintEp01.hintOrder.eq(hintNumber)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
