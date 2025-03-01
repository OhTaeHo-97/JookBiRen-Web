package com.ablez.jookbiren.episode3.quiz.repository;

import static com.ablez.jookbiren.episode3.quiz.entity.QQuiz3Ep03.quiz3Ep03;
import static com.ablez.jookbiren.episode3.quiz.entity.QQuizEp03.quizEp03;

import com.ablez.jookbiren.episode3.quiz.entity.Quiz3Ep03;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep3Quiz3QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep3Quiz3QuerydslRepository() {
        super(Quiz3Ep03.class);
    }

    public List<Quiz3Ep03> findAllQuiz3IsAnswer(UserEp03 user) {
        return selectFrom(quiz3Ep03)
                .where(
                        quiz3Ep03.userId.eq(user),
                        quiz3Ep03.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz3Ep03> findByQuizNumberAndUser3(int quizNumber, UserEp03 user) {
        Quiz3Ep03 quiz = selectFrom(quiz3Ep03)
                .join(quiz3Ep03.quiz, quizEp03)
                .where(
                        quizEp03.placeCode.eq(3),
                        quizEp03.quizNumber.eq(quizNumber),
                        quiz3Ep03.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
