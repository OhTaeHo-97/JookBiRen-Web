package com.ablez.jookbiren.episode2.quiz.service;

import com.ablez.jookbiren.episode2.quiz.entity.Quiz0Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz1Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz2Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz3Ep02;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz0QuerydslRepository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz0Repository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz1QuerydslRepository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz1Repository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz2QuerydslRepository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz2Repository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz3QuerydslRepository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2Quiz3Repository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2QuizRepository;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class Ep2QuizInfoService {
    private final Ep2Quiz0QuerydslRepository quiz0Repository;
    private final Ep2Quiz1QuerydslRepository quiz1Repository;
    private final Ep2Quiz2QuerydslRepository quiz2Repository;
    private final Ep2Quiz3QuerydslRepository quiz3Repository;
    private final Ep2Quiz0Repository quiz0JpaRepository;
    private final Ep2Quiz1Repository quiz1JpaRepository;
    private final Ep2Quiz2Repository quiz2JpaRepository;
    private final Ep2Quiz3Repository quiz3JpaRepository;
    private final Ep2QuizRepository quizRepository;
//    private final QuizInfoMapper quizInfoMapper;

    @Transactional(readOnly = true)
    public Optional<Quiz0Ep02> findByQuizNumberAndUser0(int quizNumber, UserEp02 user) {
        return quiz0Repository.findByQuizNumberAndUser0(quizNumber, user);
    }

    @Transactional(readOnly = true)
    public Optional<Quiz1Ep02> findByQuizNumberAndUser1(int quizNumber, UserEp02 user) {
        return quiz1Repository.findByQuizNumberAndUser1(quizNumber, user);
    }

    @Transactional(readOnly = true)
    public Optional<Quiz2Ep02> findByQuizNumberAndUser2(int quizNumber, UserEp02 user) {
        return quiz2Repository.findByQuizNumberAndUser2(quizNumber, user);
    }

    @Transactional(readOnly = true)
    public Optional<Quiz3Ep02> findByQuizNumberAndUser3(int quizNumber, UserEp02 user) {
        return quiz3Repository.findByQuizNumberAndUser3(quizNumber, user);
    }

    @Transactional(readOnly = true)
    public List<Quiz0Ep02> getHyeHwaSolvedQuizzes(UserEp02 user) {
        return quiz0Repository.findAllQuiz0IsAnswer(user);
    }

    @Transactional(readOnly = true)
    public List<Quiz1Ep02> getIHwaSolvedQuizzes(UserEp02 user) {
        return quiz1Repository.findAllQuiz1IsAnswer(user);
    }

    @Transactional(readOnly = true)
    public List<Quiz2Ep02> getNaksanSolvedQuizzes(UserEp02 user) {
        return quiz2Repository.findAllQuiz2IsAnswer(user);
    }

    @Transactional(readOnly = true)
    public Optional<Quiz2Ep02> getNaksanSolvedQuizzesByQuizNumber(int quizNumber, UserEp02 user) {
        return quiz2Repository.findQuiz2IsAnswerByQuizNumber(quizNumber, user);
    }

    @Transactional(readOnly = true)
    public List<Quiz3Ep02> getFinalSolvedQuizzes(UserEp02 user) {
        return quiz3Repository.findAllQuiz3IsAnswer(user);
    }

    public Quiz0Ep02 insertQuiz0(int quizNumber, UserEp02 user) {
        return quiz0JpaRepository.save(new Quiz0Ep02(quizNumber, user, quizRepository.findQuiz(0, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz1Ep02 insertQuiz1(int quizNumber, UserEp02 user) {
        return quiz1JpaRepository.save(new Quiz1Ep02(quizNumber, user, quizRepository.findQuiz(1, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz2Ep02 insertQuiz2(int quizNumber, UserEp02 user) {
        return quiz2JpaRepository.save(new Quiz2Ep02(quizNumber, user, quizRepository.findQuiz(2, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

    public Quiz3Ep02 insertQuiz3(int quizNumber, UserEp02 user) {
        return quiz3JpaRepository.save(new Quiz3Ep02(quizNumber, user, quizRepository.findQuiz(3, quizNumber)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND))));
    }

//    @Transactional(readOnly = true)
//    public List<QuizInfoDto> getQuizInfos() {
//        List<QuizInfoDto> quizInfos = new ArrayList<>();
//        quizInfos.addAll(getQuiz0Infos());
//        quizInfos.addAll(getQuiz1Infos());
//        quizInfos.addAll(getQuiz2Infos());
//        quizInfos.addAll(getQuiz3Infos());
//        return quizInfos;
//    }
//
//    private List<QuizInfoDto> getQuiz0Infos() {
//        List<Quiz0Ep02> quizzes = quiz0Repository.findAllQuiz0();
//        return quizzes.stream().map(quizInfoMapper::makeQuizInfoDto).collect(Collectors.toList());
//    }
//
//    private List<QuizInfoDto> getQuiz1Infos() {
//        List<Quiz1Ep02> quizzes = quiz1Repository.findAllQuiz1();
//        return quizzes.stream().map(quizInfoMapper::makeQuizInfoDto).collect(Collectors.toList());
//    }
//
//    private List<QuizInfoDto> getQuiz2Infos() {
//        List<Quiz2Ep02> quizzes = quiz2Repository.findAllQuiz2();
//        return quizzes.stream().map(quizInfoMapper::makeQuizInfoDto).collect(Collectors.toList());
//    }
//
//    private List<QuizInfoDto> getQuiz3Infos() {
//        List<Quiz3Ep02> quizzes = quiz3Repository.findAllQuiz3();
//        return quizzes.stream().map(quizInfoMapper::makeQuizInfoDto).collect(Collectors.toList());
//    }
}
