package com.ablez.jookbiren.episode3.quiz.service;

import com.ablez.jookbiren.episode3.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode3.answer.service.Ep3AnswerService;
import com.ablez.jookbiren.episode3.dto.JookbirenDto.Quiz;
import com.ablez.jookbiren.episode3.hint.entity.HintEp03;
import com.ablez.jookbiren.episode3.hint.service.Ep3HintService;
import com.ablez.jookbiren.episode3.quiz.dto.QuizDto.HintDto;
import com.ablez.jookbiren.episode3.quiz.dto.QuizDto.PageDto;
import com.ablez.jookbiren.episode3.quiz.dto.QuizDto.QuizPageDto;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz0Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz1Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz2Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz3Ep03;
import com.ablez.jookbiren.episode3.user.entity.UserEp03;
import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class Ep3QuizService {
    private final Ep3AnswerService answerService;
    private final Ep3QuizInfoService quizInfoService;
    private final Ep3HintService hintService;

    public PageDto getCurrentSituationAndSolvedProblems(int placeCode, UserEp03 user) {
        if (placeCode == 0) {
            return getCurrentSituationAndEntireSolvedProblems(user);
        } else if (placeCode == 1) {
            return getCurrentSituationAndCafeStreetSolvedProblems(user);
        } else if (placeCode == 2) {
            return getCurrentSituationAndSeoulForestSolvedProblems(user);
        } else if (placeCode == 3) {
            return getCurrentSituationAndFinalSolvedProblems(user);
        }

        throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
    }

    private PageDto getCurrentSituationAndFinalSolvedProblems(UserEp03 user) {
        List<Quiz3Ep03> solvedQuizzes = quizInfoService.getFinalSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndSeoulForestSolvedProblems(UserEp03 user) {
        List<Quiz2Ep03> solvedQuizzes = quizInfoService.getSeoulForestSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndCafeStreetSolvedProblems(UserEp03 user) {
        List<Quiz1Ep03> solvedQuizzes = quizInfoService.getCafeStreetSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndEntireSolvedProblems(UserEp03 user) {
        List<Quiz0Ep03> solvedQuizzes = quizInfoService.getEntireSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    public QuizPageDto checkAlreadySolvedQuiz(UserEp03 user, Quiz quizInfo) {
        if (quizInfo.getPlaceCode() == 0) {
            return findAnswerOfEntire(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 1) {
            return findAnswerOfCafeStreet(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 2) {
            return findAnswerOfSeoulForest(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 3) {
            return findAnswerOfFinal(user, quizInfo);
        } else {
            throw new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND); // 잘못된 퀴즈 정보
        }
    }

    private QuizPageDto findAnswerOfFinal(UserEp03 user, Quiz quizInfo) {
        Quiz3Ep03 quiz = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz3Ep03 newQuiz = quizInfoService.insertQuiz3(quizInfo.getQuizNumber(), user);
            user.setQuiz3s(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    private QuizPageDto findAnswerOfSeoulForest(UserEp03 user, Quiz quizInfo) {
        Quiz2Ep03 quiz = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz2Ep03 newQuiz = quizInfoService.insertQuiz2(quizInfo.getQuizNumber(), user);
            user.addQuiz2(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    private QuizPageDto findAnswerOfCafeStreet(UserEp03 user, Quiz quizInfo) {
        Quiz1Ep03 quiz = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz1Ep03 newQuiz = quizInfoService.insertQuiz1(quizInfo.getQuizNumber(), user);
            user.addQuiz1(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    private QuizPageDto findAnswerOfEntire(UserEp03 user, Quiz quizInfo) {
        Quiz0Ep03 quiz = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz0Ep03 newQuiz = quizInfoService.insertQuiz0(quizInfo.getQuizNumber(), user);
            user.addQuiz0(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    public HintDto findHint(int hintNumber, UserEp03 user, Quiz quizInfo) {
        String hint = null;
        String image = null;

        if (quizInfo.getPlaceCode() == 0) {
            Quiz0Ep03 log = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (log == null) {
                log = quizInfoService.insertQuiz0(quizInfo.getQuizNumber(), user);
            }

            Optional<HintEp03> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
                    quizInfo.getQuizNumber(), hintNumber);
            if (hintInfo.isEmpty()) {
                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
            }

            if (hintNumber == 1) {
                log.setFirstHintTime(log.getFirstHintTime() == null ? LocalDateTime.now() : log.getFirstHintTime());
            } else if (hintNumber == 2) {
                log.setSecondHintTime(
                        log.getSecondHintTime() == null ? LocalDateTime.now() : log.getSecondHintTime());
            }

            hint = hintInfo.get().getHint();
            image = hintInfo.get().getHintImage();
        } else if (quizInfo.getPlaceCode() == 1) {
            Quiz1Ep03 log = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (log == null) {
                log = quizInfoService.insertQuiz1(quizInfo.getQuizNumber(), user);
            }

            Optional<HintEp03> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
                    quizInfo.getQuizNumber(), hintNumber);
            if (hintInfo.isEmpty()) {
                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
            }

            if (hintNumber == 1) {
                log.setFirstHintTime(log.getFirstHintTime() == null ? LocalDateTime.now() : log.getFirstHintTime());
            } else if (hintNumber == 2) {
                log.setSecondHintTime(
                        log.getSecondHintTime() == null ? LocalDateTime.now() : log.getSecondHintTime());
            }

            hint = hintInfo.get().getHint();
            image = hintInfo.get().getHintImage();
        } else if (quizInfo.getPlaceCode() == 2) {
            Quiz2Ep03 log = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (log == null) {
                log = quizInfoService.insertQuiz2(quizInfo.getQuizNumber(), user);
            }

            Optional<HintEp03> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
                    quizInfo.getQuizNumber(), hintNumber);
            if (hintInfo.isEmpty()) {
                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
            }

            if (hintNumber == 1) {
                log.setFirstHintTime(log.getFirstHintTime() == null ? LocalDateTime.now() : log.getFirstHintTime());
            } else if (hintNumber == 2) {
                log.setSecondHintTime(
                        log.getSecondHintTime() == null ? LocalDateTime.now() : log.getSecondHintTime());
            }

            hint = hintInfo.get().getHint();
            image = hintInfo.get().getHintImage();
        } else if (quizInfo.getPlaceCode() == 3) {
            Quiz3Ep03 log = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
            if (log == null) {
                log = quizInfoService.insertQuiz3(quizInfo.getQuizNumber(), user);
            }

            Optional<HintEp03> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
                    quizInfo.getQuizNumber(), hintNumber);
            if (hintInfo.isEmpty()) {
                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
            }

            log.setGetHintTime(log.getGetHintTime() == null ? LocalDateTime.now() : log.getGetHintTime());

            hint = hintInfo.get().getHint();
            image = hintInfo.get().getHintImage();
        } else {
            throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
        }

        return new HintDto(hint, image);
    }
}
