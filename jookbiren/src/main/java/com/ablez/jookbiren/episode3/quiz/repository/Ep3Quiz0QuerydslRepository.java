package com.ablez.jookbiren.episode3.quiz.repository;

import static com.ablez.jookbiren.episode3.quiz.entity.QQuiz0Ep03.quiz0Ep03;
import static com.ablez.jookbiren.episode3.quiz.entity.QQuizEp03.quizEp03;

import com.ablez.jookbiren.episode3.quiz.entity.Quiz0Ep03;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Ep3Quiz0QuerydslRepository extends Querydsl4RepositorySupport {
    public Ep3Quiz0QuerydslRepository() {
        super(Quiz0Ep03.class);
    }

    public List<Quiz0Ep03> findAllQuiz0(UserEp03 user) {
        return selectFrom(quiz0Ep03)
                .where(quiz0Ep03.userId.eq(user))
                .fetch();
    }

    public List<Quiz0Ep03> findAllQuiz0IsAnswer(UserEp03 user) {
        return selectFrom(quiz0Ep03)
                .where(
                        quiz0Ep03.userId.eq(user),
                        quiz0Ep03.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz0Ep03> findByQuizNumberAndUser0(int quizNumber, UserEp03 user) {
        Quiz0Ep03 quiz = selectFrom(quiz0Ep03)
                .join(quiz0Ep03.quiz, quizEp03)
                .where(
                        quizEp03.placeCode.eq(0),
                        quizEp03.quizNumber.eq(quizNumber),
                        quiz0Ep03.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
