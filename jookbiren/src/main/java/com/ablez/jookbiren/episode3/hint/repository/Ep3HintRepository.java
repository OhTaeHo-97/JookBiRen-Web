package com.ablez.jookbiren.episode3.hint.repository;

import static com.ablez.jookbiren.episode3.hint.entity.QHintEp03.hintEp03;
import static com.ablez.jookbiren.episode3.quiz.entity.QQuizEp03.quizEp03;

import com.ablez.jookbiren.episode3.hint.entity.HintEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep3HintRepository extends Querydsl4RepositorySupport {
    public Ep3HintRepository() {
        super(HintEp03.class);
    }

    public Optional<HintEp03> findByQuizAndHintNumber(int placeCode, int quizNumber, int hintNumber) {
        HintEp03 result = selectFrom(hintEp03)
                .innerJoin(hintEp03.quiz, quizEp03).fetchJoin()
                .where(
                        hintEp03.quiz.placeCode.eq(placeCode),
                        hintEp03.quiz.quizNumber.eq(quizNumber),
                        hintEp03.hintOrder.eq(hintNumber)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
