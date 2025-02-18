package com.ablez.jookbiren.episode3.quiz.repository;

import static com.ablez.jookbiren.episode3.quiz.entity.QQuiz2Ep03.quiz2Ep03;
import static com.ablez.jookbiren.episode3.quiz.entity.QQuizEp03.quizEp03;

import com.ablez.jookbiren.episode3.quiz.entity.Quiz2Ep03;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Quiz2QuerydslRepository extends Querydsl4RepositorySupport {
    public Quiz2QuerydslRepository() {
        super(Quiz2Ep03.class);
    }

    public List<Quiz2Ep03> findAllQuiz2(UserEp03 user) {
        return selectFrom(quiz2Ep03)
                .where(quiz2Ep03.userId.eq(user))
                .fetch();
    }

    public List<Quiz2Ep03> findAllQuiz2IsAnswer(UserEp03 user) {
        return selectFrom(quiz2Ep03)
                .where(
                        quiz2Ep03.userId.eq(user),
                        quiz2Ep03.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz2Ep03> findByQuizNumberAndUser2(int quizNumber, UserEp03 user) {
        Quiz2Ep03 quiz = selectFrom(quiz2Ep03)
                .join(quiz2Ep03.quiz, quizEp03)
                .where(
                        quizEp03.placeCode.eq(2),
                        quizEp03.quizNumber.eq(quizNumber),
                        quiz2Ep03.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
