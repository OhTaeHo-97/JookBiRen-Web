package com.ablez.jookbiren.episode3.quiz.service;

import com.ablez.jookbiren.episode3.quiz.entity.Quiz0Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz1Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz2Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz3Ep03;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz0QuerydslRepository;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz0Repository;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz1QuerydslRepository;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz1Repository;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz2QuerydslRepository;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz2Repository;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz3QuerydslRepository;
import com.ablez.jookbiren.episode3.quiz.repository.Quiz3Repository;
import com.ablez.jookbiren.episode3.quiz.repository.QuizRepository;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class QuizInfoService {
    private final QuizRepository quizRepository;
    private final Quiz0Repository quiz0Repository;
    private final Quiz0QuerydslRepository quiz0QuerydslRepository;
    private final Quiz1Repository quiz1Repository;
    private final Quiz1QuerydslRepository quiz1QuerydslRepository;
    private final Quiz2Repository quiz2Repository;
    private final Quiz2QuerydslRepository quiz2QuerydslRepository;
    private final Quiz3Repository quiz3Repository;
    private final Quiz3QuerydslRepository quiz3QuerydslRepository;

    public List<Quiz3Ep03> getFinalSolvedQuizzes(UserEp03 user) {
        return quiz3QuerydslRepository.findAllQuiz3IsAnswer(user);
    }

    public List<Quiz2Ep03> getSeoulForestSolvedQuizzes(UserEp03 user) {
        return quiz2QuerydslRepository.findAllQuiz2IsAnswer(user);
    }

    public List<Quiz1Ep03> getCafeStreetSolvedQuizzes(UserEp03 user) {
        return quiz1QuerydslRepository.findAllQuiz1IsAnswer(user);
    }

    public List<Quiz0Ep03> getEntireSolvedQuizzes(UserEp03 user) {
        return quiz0QuerydslRepository.findAllQuiz0IsAnswer(user);
    }

    public Quiz0Ep03 insertQuiz0(int quizNumber, UserEp03 user) {
        return quiz0Repository.save(new Quiz0Ep03(quizNumber, user, quizRepository.findQuiz(0, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz1Ep03 insertQuiz1(int quizNumber, UserEp03 user) {
        return quiz1Repository.save(new Quiz1Ep03(quizNumber, user, quizRepository.findQuiz(1, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz2Ep03 insertQuiz2(int quizNumber, UserEp03 user) {
        return quiz2Repository.save(new Quiz2Ep03(quizNumber, user, quizRepository.findQuiz(2, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz3Ep03 insertQuiz3(int quizNumber, UserEp03 user) {
        return quiz3Repository.save(new Quiz3Ep03(quizNumber, user, quizRepository.findQuiz(3, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Optional<Quiz0Ep03> findByQuizNumberAndUser0(int quizNumber, UserEp03 user) {
        return quiz0QuerydslRepository.findByQuizNumberAndUser0(quizNumber, user);
    }

    public Optional<Quiz1Ep03> findByQuizNumberAndUser1(int quizNumber, UserEp03 user) {
        return quiz1QuerydslRepository.findByQuizNumberAndUser1(quizNumber, user);
    }

    public Optional<Quiz2Ep03> findByQuizNumberAndUser2(int quizNumber, UserEp03 user) {
        return quiz2QuerydslRepository.findByQuizNumberAndUser2(quizNumber, user);
    }

    public Optional<Quiz3Ep03> findByQuizNumberAndUser3(int quizNumber, UserEp03 user) {
        return quiz3QuerydslRepository.findByQuizNumberAndUser3(quizNumber, user);
    }
}
