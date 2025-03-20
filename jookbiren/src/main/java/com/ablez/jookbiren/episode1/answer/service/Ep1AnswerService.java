package com.ablez.jookbiren.episode1.answer.service;

import static com.ablez.jookbiren.episode1.utils.JookbirenConstant.STAR_QUIZ_COUNT;

import com.ablez.jookbiren.episode1.answer.dto.AnswerDto.CheckAnswerDto;
import com.ablez.jookbiren.episode1.answer.dto.AnswerDto.CheckAnswerResponseDto;
import com.ablez.jookbiren.episode1.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode1.answer.dto.AnswerDto.SuspectDto;
import com.ablez.jookbiren.episode1.answer.dto.AnswerDto.SuspectResponseDto;
import com.ablez.jookbiren.episode1.answer.entity.AnswerEp01;
import com.ablez.jookbiren.episode1.answer.repository.Ep1AnswerRepository;
import com.ablez.jookbiren.episode1.answer.utils.AnswerConstant;
import com.ablez.jookbiren.episode1.dto.JookbirenDto.Quiz;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz0Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz1Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz2Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz3Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz4Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.QuizEp01;
import com.ablez.jookbiren.episode1.quiz.entity.WrongAnswerEp01;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1QuizRepository;
import com.ablez.jookbiren.episode1.quiz.repository.Ep1WrongAnswerRepository;
import com.ablez.jookbiren.episode1.quiz.service.Ep1QuizInfoService;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
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
public class Ep1AnswerService {
    private final Ep1AnswerRepository answerRepository;
    private final Ep1QuizInfoService quizInfoService;
    private final Ep1QuizRepository quizRepository;
    private final Ep1WrongAnswerRepository wrongAnswerRepository;

    public FindAnswerResponseDto findAnswer(Quiz quizInfo) {
        AnswerEp01 answer = answerRepository.findByQuiz(quizInfo.getPlaceCode(), quizInfo.getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        return new FindAnswerResponseDto(answer.getAnswer());
    }

    public FindAnswerResponseDto findAnswer(Quiz quizInfo, UserEp01 user) {
        AnswerEp01 answer = answerRepository.findByQuiz(quizInfo.getPlaceCode(), quizInfo.getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        if (quizInfo.getPlaceCode() == 0) {
            Quiz0Ep01 quiz = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 1) {
            Quiz1Ep01 quiz = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 2) {
            Quiz2Ep01 quiz = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 3) {
            Quiz3Ep01 quiz = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 4) {
            Quiz4Ep01 quiz = quizInfoService.findByQuizNumberAndUser4(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        }

        return new FindAnswerResponseDto(answer.getAnswer());
    }

    public CheckAnswerResponseDto checkAnswer(CheckAnswerDto answerInfo, UserEp01 user) {
        Optional<AnswerEp01> optionalAnswer = answerRepository.findByQuizAndAnswer(
                answerInfo.getQuizInfo().getPlaceCode(),
                answerInfo.getQuizInfo().getQuizNumber(), answerInfo.getAnswer());
        QuizEp01 quiz = quizRepository.findQuiz(answerInfo.getQuizInfo().getPlaceCode(),
                        answerInfo.getQuizInfo().getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        if (optionalAnswer.isPresent()) {
            updateUserAnswerStatus(quiz, user);
            setAnswerTime(answerInfo, user);
            return new CheckAnswerResponseDto(true);
        } else {
            wrongAnswerRepository.save(new WrongAnswerEp01(answerInfo.getAnswer(), LocalDateTime.now(), user, quiz));
            return new CheckAnswerResponseDto(false);
        }
    }

    private void updateUserAnswerStatus(QuizEp01 quiz, UserEp01 user) {
        if (quiz.getQuizCode() == -1) {
            return;
        }
        int answerStatus = user.getAnswerStatusCode();
        user.setAnswerStatusCode(answerStatus | (1 << quiz.getQuizCode()));
    }

    private void updateSolvedQuizCount(UserEp01 user) {
        user.setSolvedQuizCount(user.getSolvedQuizCount() + 1);
    }

    private void setAnswerTime(CheckAnswerDto answerInfo, UserEp01 user) {
        if (answerInfo.getQuizInfo().getPlaceCode() == 0) {
            Quiz0Ep01 quiz = quizInfoService.findByQuizNumberAndUser0(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 1) {
            Quiz1Ep01 quiz = quizInfoService.findByQuizNumberAndUser1(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 2) {
            Quiz2Ep01 quiz = quizInfoService.findByQuizNumberAndUser2(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 3) {
            Quiz3Ep01 quiz = quizInfoService.findByQuizNumberAndUser3(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 4) {
            Quiz4Ep01 quiz = quizInfoService.findByQuizNumberAndUser4(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        }
    }

    public SuspectResponseDto pickSuspect(UserEp01 user, SuspectDto suspectInfo) {
        if (user.getAnswerStatusCode() != (1 << STAR_QUIZ_COUNT) - 1) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_PICK_SUSPECT);
        }

        if (user.getCriminal() == 0) {
            user.updateCriminal(suspectInfo.getSuspect());
            user.setAnswerTime(LocalDateTime.now());
            user.setScore(calculateScore(user));
            return new SuspectResponseDto();
        } else {
            return new SuspectResponseDto(AnswerConstant.SUSPECT.get(user.getCriminal()));
        }
    }

    private int calculateScore(UserEp01 user) {
        int score = 0;
        if (user.getCriminal() == 2) {
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

        if (user.getSolvedQuizCount() >= 19) {
            score += 5;
        } else if (user.getSolvedQuizCount() >= 16) {
            score += 3;
        } else if (user.getSolvedQuizCount() >= 13) {
            score += 2;
        } else {
            score += 1;
        }

        return score;
    }
}
