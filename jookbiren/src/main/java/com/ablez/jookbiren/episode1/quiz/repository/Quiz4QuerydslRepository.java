package com.ablez.jookbiren.episode1.quiz.repository;

import static com.ablez.jookbiren.episode1.quiz.entity.QQuiz4Ep01.quiz4Ep01;
import static com.ablez.jookbiren.episode1.quiz.entity.QQuizEp01.quizEp01;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz4Ep01;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.repository.Querydsl4RepositorySupport;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class Quiz4QuerydslRepository extends Querydsl4RepositorySupport {
    public Quiz4QuerydslRepository() {
        super(Quiz4Ep01.class);
    }

    public List<Quiz4Ep01> findAllQuiz4IsAnswer(UserEp01 user) {
        return selectFrom(quiz4Ep01)
                .where(
                        quiz4Ep01.userId.eq(user),
                        quiz4Ep01.firstAnswerTime.isNotNull()
                )
                .fetch();
    }

    public Optional<Quiz4Ep01> findByQuizNumberAndUser4(int quizNumber, UserEp01 user) {
        Quiz4Ep01 quiz = selectFrom(quiz4Ep01)
                .join(quiz4Ep01.quiz, quizEp01)
                .where(
                        quizEp01.placeCode.eq(4),
                        quizEp01.quizNumber.eq(quizNumber),
                        quiz4Ep01.userId.eq(user)
                )
                .distinct()
                .fetchOne();

        return Optional.ofNullable(quiz);
    }
}
