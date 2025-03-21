package com.ablez.jookbiren.episode3.answer.service;

import static com.ablez.jookbiren.episode3.utils.JookbirenConstant.STAR_QUIZ_COUNT;

import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.CheckAnswerDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.CheckAnswerResponseDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.SuspectDto;
import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.SuspectResponseDto;
import com.ablez.jookbiren.episode3.answer.entity.AnswerEp03;
import com.ablez.jookbiren.episode3.answer.repository.Ep3AnswerRepository;
import com.ablez.jookbiren.episode3.answer.utils.AnswerConstant;
import com.ablez.jookbiren.episode3.dto.JookbirenDto.Quiz;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz0Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz1Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz2Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz3Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.QuizEp03;
import com.ablez.jookbiren.episode3.quiz.entity.WrongAnswerEp03;
import com.ablez.jookbiren.episode3.quiz.repository.Ep3QuizRepository;
import com.ablez.jookbiren.episode3.quiz.repository.Ep3WrongAnswerRepository;
import com.ablez.jookbiren.episode3.quiz.service.Ep3QuizInfoService;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
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
public class Ep3AnswerService {
    private final Ep3AnswerRepository answerRepository;
    private final Ep3QuizInfoService quizInfoService;
    private final Ep3QuizRepository quizRepository;
    private final Ep3WrongAnswerRepository wrongAnswerRepository;

    public FindAnswerResponseDto findAnswer(Quiz quizInfo) {
        AnswerEp03 answer = answerRepository.findByQuiz(quizInfo.getPlaceCode(), quizInfo.getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        return new FindAnswerResponseDto(answer.getAnswer());
    }

    public FindAnswerResponseDto findAnswer(Quiz quizInfo, UserEp03 user) {
        AnswerEp03 answer = answerRepository.findByQuiz(quizInfo.getPlaceCode(), quizInfo.getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        if (quizInfo.getPlaceCode() == 0) {
            Quiz0Ep03 quiz = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));

            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 1) {
            Quiz1Ep03 quiz = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));

            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 2) {
            Quiz2Ep03 quiz = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));

            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        } else if (quizInfo.getPlaceCode() == 3) {
            Quiz3Ep03 quiz = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));

            if (quiz.getGetAnswerTime() == null) {
                quiz.setGetAnswerTime(LocalDateTime.now());
                user.setAnswerCount(user.getAnswerCount() + 1);
            }
        }

        return new FindAnswerResponseDto(answer.getAnswer());
    }

    public CheckAnswerResponseDto checkAnswer(CheckAnswerDto answerInfo, UserEp03 user) {
        Optional<AnswerEp03> optionalAnswer = answerRepository.findByQuizAndAnswer(
                answerInfo.getQuizInfo().getPlaceCode(),
                answerInfo.getQuizInfo().getQuizNumber(), answerInfo.getAnswer());
        QuizEp03 quiz = quizRepository.findQuiz(answerInfo.getQuizInfo().getPlaceCode(),
                        answerInfo.getQuizInfo().getQuizNumber())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND));
        if (optionalAnswer.isPresent()) {
            updateUserAnswerStatus(quiz, user);
            setAnswerTime(answerInfo, user);
            return new CheckAnswerResponseDto(true);
        } else {
            wrongAnswerRepository.save(new WrongAnswerEp03(answerInfo.getAnswer(), LocalDateTime.now(), user, quiz));
            return new CheckAnswerResponseDto(false);
        }
    }

    private void updateUserAnswerStatus(QuizEp03 quiz, UserEp03 user) {
        if (quiz.getQuizCode() == -1) {
            return;
        }
        int answerStatus = user.getAnswerStatusCode();
        user.setAnswerStatusCode(answerStatus | (1 << quiz.getQuizCode()));
    }

    private void updateSolvedQuizCount(UserEp03 user) {
        user.setSolvedQuizCount(user.getSolvedQuizCount() + 1);
    }

    private void setAnswerTime(CheckAnswerDto answerInfo, UserEp03 user) {
        if (answerInfo.getQuizInfo().getPlaceCode() == 0) {
            Quiz0Ep03 quiz = quizInfoService.findByQuizNumberAndUser0(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 1) {
            Quiz1Ep03 quiz = quizInfoService.findByQuizNumberAndUser1(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 2) {
            Quiz2Ep03 quiz = quizInfoService.findByQuizNumberAndUser2(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        } else if (answerInfo.getQuizInfo().getPlaceCode() == 3) {
            Quiz3Ep03 quiz = quizInfoService.findByQuizNumberAndUser3(answerInfo.getQuizInfo().getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (quiz.getFirstAnswerTime() == null) {
                quiz.setFirstAnswerTime(LocalDateTime.now());
                updateSolvedQuizCount(user);
            }
        }
    }

    public SuspectResponseDto pickSuspect(UserEp03 user, SuspectDto suspectInfo) {
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

    private int calculateScore(UserEp03 user) {
        int score = 0;
        if (user.getCriminal() == 1) {
            score += 5;
        } else {
            score += 1;
        }

        if (user.getAnswerCount() <= 1) {
            score += 5;
        } else if (user.getAnswerCount() <= 3) {
            score += 4;
        } else if (user.getAnswerCount() <= 5) {
            score += 3;
        } else {
            score += 1;
        }

        if (user.getSolvedQuizCount() >= 17) {
            score += 5;
        } else if (user.getSolvedQuizCount() >= 14) {
            score += 3;
        } else if (user.getSolvedQuizCount() >= 11) {
            score += 2;
        } else {
            score += 1;
        }

        return score;
    }
}
