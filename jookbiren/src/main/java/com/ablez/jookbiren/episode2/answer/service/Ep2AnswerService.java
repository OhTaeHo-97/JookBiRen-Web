package com.ablez.jookbiren.episode2.answer.service;

import static com.ablez.jookbiren.episode2.utils.JookBiRenConstant.STAR_QUIZ_COUNT;

import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.CheckAnswerDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.CheckAnswerResponseDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.SuspectDto;
import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.SuspectResponseDto;
import com.ablez.jookbiren.episode2.answer.entity.AnswerEp02;
import com.ablez.jookbiren.episode2.answer.repository.Ep2AnswerRepository;
import com.ablez.jookbiren.episode2.dto.JookBiRenDto.Quiz;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz0Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz1Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz2Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz3Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.QuizEp02;
import com.ablez.jookbiren.episode2.quiz.entity.WrongAnswerEp02;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2QuizRepository;
import com.ablez.jookbiren.episode2.quiz.service.Ep2QuizInfoService;
import com.ablez.jookbiren.episode2.quiz.service.Ep2WrongAnswerService;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class Ep2AnswerService {
    private final Ep2AnswerRepository answerRepository;
    private final Ep2QuizInfoService quizInfoService;
    private final Ep2QuizRepository quizRepository;
    private final Ep2WrongAnswerService wrongAnswerService;

    @Transactional(readOnly = true)
    public FindAnswerResponseDto findAnswer(Quiz quizInfo) {
        AnswerEp02 answer = answerRepository.findByQuiz(quizInfo.getPlaceCode(), quizInfo.getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        return new FindAnswerResponseDto(answer.getAnswer());
    }

    public FindAnswerResponseDto findAnswer(Quiz quizInfo, UserEp02 user) {
        AnswerEp02 answer = answerRepository.findByQuiz(quizInfo.getPlaceCode(), quizInfo.getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        if (quizInfo.getPlaceCode() == 0) {
            Quiz0Ep02 quiz = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 1) {
            Quiz1Ep02 quiz = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 2) {
            Quiz2Ep02 quiz = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 3) {
            Quiz3Ep02 quiz = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        }

        return new FindAnswerResponseDto(answer.getAnswer());
    }

    public CheckAnswerResponseDto checkAnswer(CheckAnswerDto answerInfo, UserEp02 user) {
        Optional<AnswerEp02> optionalAnswer = answerRepository.findByQuizAndAnswer(
                answerInfo.getQuizInfo().getPlaceCode(),
                answerInfo.getQuizInfo().getQuizNumber(), answerInfo.getAnswer());
        QuizEp02 quiz = quizRepository.findQuiz(answerInfo.getQuizInfo().getPlaceCode(),
                        answerInfo.getQuizInfo().getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        if (optionalAnswer.isPresent()) {
            updateUserAnswerStatus(quiz, user);
            setAnswerTime(answerInfo, user);
            return new CheckAnswerResponseDto(true);
        } else {
            wrongAnswerService.insertWrongAnswer(
                    new WrongAnswerEp02(answerInfo.getAnswer(), LocalDateTime.now(), user, quiz));
            return new CheckAnswerResponseDto(false);
        }
    }

    private void updateUserAnswerStatus(QuizEp02 quiz, UserEp02 user) {
        if (quiz.getQuizCode() == -1) {
            return;
        }
        int answerStatus = user.getAnswerStatusCode();
        user.setAnswerStatusCode(answerStatus | (1 << quiz.getQuizCode()));
    }

    private void setAnswerTime(CheckAnswerDto answerInfo, UserEp02 user) {
        if (answerInfo.getQuizInfo().getPlaceCode() == 0) {
            Quiz0Ep02 quiz = quizInfoService.findByQuizNumberAndUser0(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 1) {
            Quiz1Ep02 quiz = quizInfoService.findByQuizNumberAndUser1(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 2) {
            Quiz2Ep02 quiz = quizInfoService.findByQuizNumberAndUser2(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 3) {
            Quiz3Ep02 quiz = quizInfoService.findByQuizNumberAndUser3(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        }
    }

    private void updateSolvedQuizCount(UserEp02 user) {
        user.setSolvedQuizCount(user.getSolvedQuizCount() + 1);
    }

    public void pickSuspect1(UserEp02 user, SuspectDto suspectInfo) {
        if (user.getAnswerStatusCode() != (1 << STAR_QUIZ_COUNT) - 1) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_PICK_SUSPECT);
        }
        if (user.getCriminal1() == 0) {
            user.updateCriminal1(suspectInfo.getSuspect());
        }
    }

    public SuspectResponseDto pickSuspect2(UserEp02 user, SuspectDto suspectInfo) {
        if (user.getAnswerStatusCode() != (1 << STAR_QUIZ_COUNT) - 1) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_PICK_SUSPECT);
        }

        if (user.getCriminal2() == 0) {
            user.updateCriminal2(suspectInfo.getSuspect());
            user.setAnswerTime(LocalDateTime.now());
            user.setScore(calculateScore(user));
            return new SuspectResponseDto(false);
        } else {
            return new SuspectResponseDto(true);
        }
    }

    private int calculateScore(UserEp02 user) {
        int score = 0;
        if (user.getCriminal1() == 3) {
            score += 5;
        } else {
            score += 1;
        }

        if (user.getAnswerCount() == 0) {
            score += 5;
        } else if (user.getAnswerCount() <= 3) {
            score += 4;
        } else if (user.getAnswerCount() <= 5) {
            score += 3;
        } else {
            score += 1;
        }

        if (user.getSolvedQuizCount() == 16) {
            score += 5;
        } else if (user.getSolvedQuizCount() >= 13) {
            score += 3;
        } else if (user.getSolvedQuizCount() >= 10) {
            score += 2;
        } else {
            score += 1;
        }

        return score;
    }
}
