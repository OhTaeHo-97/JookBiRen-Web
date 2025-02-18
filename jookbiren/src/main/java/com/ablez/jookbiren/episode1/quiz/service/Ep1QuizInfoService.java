package com.ablez.jookbiren.episode1.quiz.service;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz0Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz1Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz2Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz3Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz4Ep01;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz0QuerydslRepository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz0Repository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz1QuerydslRepository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz1Repository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz2QuerydslRepository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz2Repository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz3QuerydslRepository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz3Repository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz4QuerydslRepository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1Quiz4Repository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1QuizRepository;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
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
public class Ep1QuizInfoService {
    private final Ep1QuizRepository quizRepository;
    private final Ep1Quiz0Repository quiz0Repository;
    private final Ep1Quiz0QuerydslRepository quiz0QuerydslRepository;
    private final Ep1Quiz1Repository quiz1Repository;
    private final Ep1Quiz1QuerydslRepository quiz1QuerydslRepository;
    private final Ep1Quiz2Repository quiz2Repository;
    private final Ep1Quiz2QuerydslRepository quiz2QuerydslRepository;
    private final Ep1Quiz3Repository quiz3Repository;
    private final Ep1Quiz3QuerydslRepository quiz3QuerydslRepository;
    private final Ep1Quiz4Repository quiz4Repository;
    private final Ep1Quiz4QuerydslRepository quiz4QuerydslRepository;

    public List<Quiz4Ep01> getGangnamSolvedQuizzes(UserEp01 user) {
        return quiz4QuerydslRepository.findAllQuiz4IsAnswer(user);
    }

    public List<Quiz3Ep01> getSsamzigilSolvedQuizzes(UserEp01 user) {
        return quiz3QuerydslRepository.findAllQuiz3IsAnswer(user);
    }

    public List<Quiz2Ep01> getBukchonSolvedQuizzes(UserEp01 user) {
        return quiz2QuerydslRepository.findAllQuiz2IsAnswer(user);
    }

    public List<Quiz1Ep01> getChangdeokgungSolvedQuizzes(UserEp01 user) {
        return quiz1QuerydslRepository.findAllQuiz1IsAnswer(user);
    }

    public List<Quiz0Ep01> getAngukSolvedQuizzes(UserEp01 user) {
        return quiz0QuerydslRepository.findAllQuiz0IsAnswer(user);
    }

    public Quiz0Ep01 insertQuiz0(int quizNumber, UserEp01 user) {
        return quiz0Repository.save(new Quiz0Ep01(quizNumber, user, quizRepository.findQuiz(0, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz1Ep01 insertQuiz1(int quizNumber, UserEp01 user) {
        return quiz1Repository.save(new Quiz1Ep01(quizNumber, user, quizRepository.findQuiz(1, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz2Ep01 insertQuiz2(int quizNumber, UserEp01 user) {
        return quiz2Repository.save(new Quiz2Ep01(quizNumber, user, quizRepository.findQuiz(2, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz3Ep01 insertQuiz3(int quizNumber, UserEp01 user) {
        return quiz3Repository.save(new Quiz3Ep01(quizNumber, user, quizRepository.findQuiz(3, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz4Ep01 insertQuiz4(int quizNumber, UserEp01 user) {
        return quiz4Repository.save(new Quiz4Ep01(quizNumber, user, quizRepository.findQuiz(4, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Optional<Quiz0Ep01> findByQuizNumberAndUser0(int quizNumber, UserEp01 user) {
        return quiz0QuerydslRepository.findByQuizNumberAndUser0(quizNumber, user);
    }

    public Optional<Quiz1Ep01> findByQuizNumberAndUser1(int quizNumber, UserEp01 user) {
        return quiz1QuerydslRepository.findByQuizNumberAndUser1(quizNumber, user);
    }

    public Optional<Quiz2Ep01> findByQuizNumberAndUser2(int quizNumber, UserEp01 user) {
        return quiz2QuerydslRepository.findByQuizNumberAndUser2(quizNumber, user);
    }

    public Optional<Quiz3Ep01> findByQuizNumberAndUser3(int quizNumber, UserEp01 user) {
        return quiz3QuerydslRepository.findByQuizNumberAndUser3(quizNumber, user);
    }

    public Optional<Quiz4Ep01> findByQuizNumberAndUser4(int quizNumber, UserEp01 user) {
        return quiz4QuerydslRepository.findByQuizNumberAndUser4(quizNumber, user);
    }
}
