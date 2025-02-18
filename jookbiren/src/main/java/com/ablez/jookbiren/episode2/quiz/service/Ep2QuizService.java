package com.ablez.jookbiren.episode2.quiz.service;

import com.ablez.jookbiren.episode2.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode2.answer.service.Ep2AnswerService;
import com.ablez.jookbiren.episode2.dto.JookBiRenDto.Quiz;
import com.ablez.jookbiren.episode2.hint.entity.HintEp02;
import com.ablez.jookbiren.episode2.hint.service.Ep2HintService;
import com.ablez.jookbiren.episode2.quiz.dto.QuizDto.HintDto;
import com.ablez.jookbiren.episode2.quiz.dto.QuizDto.PageDto;
import com.ablez.jookbiren.episode2.quiz.dto.QuizDto.QuizPageDto;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz0Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz1Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz2Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz3Ep02;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class Ep2QuizService {
    private final Ep2QuizInfoService quizInfoService;
    private final Ep2AnswerService answerService;
    private final Ep2HintService hintService;

    @Transactional(readOnly = true)
    public PageDto getCurrentSituationAndSolvedProblems(int placeCode, UserEp02 user) {
        if (placeCode == 0) {
            return getCurrentSituationAndHyeHwaSolvedProblems(user);
        } else if (placeCode == 1) {
            return getCurrentSituationAndIHwaSolvedProblems(user);
        } else if (placeCode == 2) {
            return getCurrentSituationAndNaksanSolvedProblems(user);
        } else if (placeCode == 3) {
            return getCurrentSituationAndFinalSolvedProblems(user);
        }

        throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
    }

    private PageDto getCurrentSituationAndHyeHwaSolvedProblems(UserEp02 user) {
        List<Quiz0Ep02> solvedQuizzes = quizInfoService.getHyeHwaSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndIHwaSolvedProblems(UserEp02 user) {
        List<Quiz1Ep02> solvedQuizzes = quizInfoService.getIHwaSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        Optional<Quiz2Ep02> quiz2Ep02 = quizInfoService.getNaksanSolvedQuizzesByQuizNumber(1, user);
        if (quiz2Ep02.isPresent()) {
            quizNumbers.add(7);
        }
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndNaksanSolvedProblems(UserEp02 user) {
        List<Integer> quizNumbers = new ArrayList<>();
        Optional<Quiz2Ep02> quiz2Ep02 = quizInfoService.getNaksanSolvedQuizzesByQuizNumber(2, user);
        if (quiz2Ep02.isPresent()) {
            quizNumbers.add(1);
        }
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndFinalSolvedProblems(UserEp02 user) {
        List<Quiz3Ep02> solvedQuizzes = quizInfoService.getFinalSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    public QuizPageDto checkAlreadySolvedQuiz(UserEp02 user, Quiz quizInfo) {
        if (quizInfo.getPlaceCode() == 0) {
            return findAnswerOfHyeHwaStation(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 1) {
            return findAnswerOfIHwaStation(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 2) {
            return findAnswerOfNaksanStation(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 3) {
            return findAnswerOfFinalStation(user, quizInfo);
        } else {
            throw new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND); // 잘못된 퀴즈 정보
        }
    }

    private QuizPageDto findAnswerOfHyeHwaStation(UserEp02 user, Quiz quizInfo) {
        Quiz0Ep02 quiz = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz0Ep02 newQuiz = quizInfoService.insertQuiz0(quizInfo.getQuizNumber(), user);
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

    private QuizPageDto findAnswerOfIHwaStation(UserEp02 user, Quiz quizInfo) {
        Quiz1Ep02 quiz = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz1Ep02 newQuiz = quizInfoService.insertQuiz1(quizInfo.getQuizNumber(), user);
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

    private QuizPageDto findAnswerOfNaksanStation(UserEp02 user, Quiz quizInfo) {
        Quiz2Ep02 quiz = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz2Ep02 newQuiz = quizInfoService.insertQuiz2(quizInfo.getQuizNumber(), user);
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

    private QuizPageDto findAnswerOfFinalStation(UserEp02 user, Quiz quizInfo) {
        Quiz3Ep02 quiz = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz3Ep02 newQuiz = quizInfoService.insertQuiz3(quizInfo.getQuizNumber(), user);
            user.setQuiz3(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    public HintDto findHint(int hintNumber, UserEp02 user, Quiz quizInfo) {
        // 힌트 보기
        //  1. 이미 푼 문제인지 확인 - 안 풀었어야 함
        //      - 이때 아직 접속도 하지 않았다면 로그 데이터 추가해주기
        //  2. 힌트 가져오기
        //  3. 힌트 본 시간 적어주기

        String hint = null;
        String image = null;

        if (quizInfo.getPlaceCode() == 0) {
            Quiz0Ep02 log = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz0(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
                        quizInfo.getQuizNumber(), hintNumber);
                if (hintInfo.isEmpty()) {
                    throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
                }

                if (hintNumber == 1) {
                    log.setFirstGetHintTime(
                            log.getFirstGetHintTime() == null ? LocalDateTime.now() : log.getFirstGetHintTime());
                } else if (hintNumber == 2) {
                    log.setSecondGetHintTime(
                            log.getSecondGetHintTime() == null ? LocalDateTime.now() : log.getSecondGetHintTime());
                }

                hint = hintInfo.get().getHint();
                image = hintInfo.get().getHintImage();
            }
        } else if (quizInfo.getPlaceCode() == 1) {
            Quiz1Ep02 log = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz1(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
                        quizInfo.getQuizNumber(), hintNumber);
                if (hintInfo.isEmpty()) {
                    throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
                }

                if (hintNumber == 1) {
                    log.setFirstGetHintTime(
                            log.getFirstGetHintTime() == null ? LocalDateTime.now() : log.getFirstGetHintTime());
                } else if (hintNumber == 2) {
                    log.setSecondGetHintTime(
                            log.getSecondGetHintTime() == null ? LocalDateTime.now() : log.getSecondGetHintTime());
                }

                hint = hintInfo.get().getHint();
                image = hintInfo.get().getHintImage();
            }
        } else if (quizInfo.getPlaceCode() == 2) {
            Quiz2Ep02 log = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz2(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
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
            }
        } else if (quizInfo.getPlaceCode() == 3) {
            Quiz3Ep02 log = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz3(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
                        quizInfo.getQuizNumber(), hintNumber);
                if (hintInfo.isEmpty()) {
                    throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
                }

                log.setGetHintTime(log.getGetHintTime() == null ? LocalDateTime.now() : log.getGetHintTime());

                hint = hintInfo.get().getHint();
                image = hintInfo.get().getHintImage();
            }
        } else {
            throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
        }

//        QuizPageDto checkAlreadySolvedQuiz = checkAlreadySolvedQuiz(user, quizInfo);
//        List<HintEp02> hints = hintService.getHintByQuiz(quizInfo.getPlaceCode(), quizInfo.getQuizNumber());
//        String hint = null;
//        String image = null;
//
//        if (checkAlreadySolvedQuiz.getAnswer().isEmpty()) {
//            if (quizInfo.getPlaceCode() == 0) {
//                Quiz0Ep02 quiz0Ep02 = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                        quizInfo.getQuizNumber(), hintNumber);
//                if (hintInfo.isEmpty()) {
//                    throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//                }
//
//                if (hintNumber == 1) {
//                    quiz0Ep02.setFirstGetHintTime(LocalDateTime.now());
//                } else if (hintNumber == 2) {
//                    quiz0Ep02.setSecondGetHintTime(LocalDateTime.now());
//                }
//                hint = hintInfo.get().getHint();
//                image = hintInfo.get().getHintImage();
//            } else if (quizInfo.getPlaceCode() == 1) {
////                Quiz1Ep02 quiz1Ep02 = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
////                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
////                if (quiz1Ep02.getGetHintTime() == null) {
////                    HintEp02 hintEp02 = hints.stream().filter(hintInfo -> hintInfo.getHintOrder() == 1).findFirst()
////                            .orElse(null);
////                    hint = hintEp02.getHint();
////                    quiz1Ep02.setGetHintTime(LocalDateTime.now());
////                }
//
//                Quiz1Ep02 quiz1Ep02 = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                        quizInfo.getQuizNumber(), hintNumber);
//                if (hintInfo.isEmpty()) {
//                    throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//                }
//
////                if (hintNumber == 1) {
////                    quiz1Ep02.setGetHintTime(LocalDateTime.now());
////                } else if (hintNumber == 2) {
////                    quiz1Ep02.setGetHintTime(LocalDateTime.now());
////                }
//                quiz1Ep02.setGetHintTime(LocalDateTime.now());
//                hint = hintInfo.get().getHint();
//                image = hintInfo.get().getHintImage();
//            } else if (quizInfo.getPlaceCode() == 2) {
//                Quiz2Ep02 quiz2Ep02 = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                        quizInfo.getQuizNumber(), hintNumber);
//                if (hintInfo.isEmpty()) {
//                    throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//                }
//
//                if (hintNumber == 1) {
//                    quiz2Ep02.setFirstHintTime(LocalDateTime.now());
//                } else if (hintNumber == 2) {
//                    quiz2Ep02.setSecondHintTime(LocalDateTime.now());
//                }
//                hint = hintInfo.get().getHint();
//                image = hintInfo.get().getHintImage();
//            } else if (quizInfo.getPlaceCode() == 3) {
//                Quiz3Ep02 quiz3Ep02 = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                        quizInfo.getQuizNumber(), hintNumber);
//                if (hintInfo.isEmpty()) {
//                    throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//                }
//
////                if (hintNumber == 1) {
////                    quiz3Ep02.setFirstGetHintTime(LocalDateTime.now());
////                } else if (hintNumber == 2) {
////                    quiz3Ep02.setSecondGetHintTime(LocalDateTime.now());
////                }
//                quiz3Ep02.setGetHintTime(LocalDateTime.now());
//                hint = hintInfo.get().getHint();
//                image = hintInfo.get().getHintImage();
//            } else {
//                throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
//            }
//        }
//
//        if (quizInfo.getPlaceCode() == 0) {
////            Quiz0Ep02 quiz0Ep02 = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user)
////                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//            Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                    quizInfo.getQuizNumber(), hintNumber);
//            if (hintInfo.isEmpty()) {
//                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//            }
//
////            if (checkAlreadySolvedQuiz.getAnswer().isEmpty()) {
////                if (hintNumber == 1) {
////                    quiz0Ep02.setFirstGetHintTime(LocalDateTime.now());
////                } else if (hintNumber == 2) {
////                    quiz0Ep02.setSecondGetHintTime(LocalDateTime.now());
////                }
////            }
//            hint = hintInfo.get().getHint();
//            image = hintInfo.get().getHintImage();
//        } else if (quizInfo.getPlaceCode() == 1) {
////                Quiz1Ep02 quiz1Ep02 = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
////                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
////                if (quiz1Ep02.getGetHintTime() == null) {
////                    HintEp02 hintEp02 = hints.stream().filter(hintInfo -> hintInfo.getHintOrder() == 1).findFirst()
////                            .orElse(null);
////                    hint = hintEp02.getHint();
////                    quiz1Ep02.setGetHintTime(LocalDateTime.now());
////                }
//
////            Quiz1Ep02 quiz1Ep02 = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
////                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//            Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                    quizInfo.getQuizNumber(), hintNumber);
//            if (hintInfo.isEmpty()) {
//                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//            }
////            Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
////                    quizInfo.getQuizNumber(), hintNumber);
////            if (hintInfo.isEmpty()) {
////                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
////            }
//
////                if (hintNumber == 1) {
////                    quiz1Ep02.setGetHintTime(LocalDateTime.now());
////                } else if (hintNumber == 2) {
////                    quiz1Ep02.setGetHintTime(LocalDateTime.now());
////                }
////            if (checkAlreadySolvedQuiz.getAnswer().isEmpty()) {
////                quiz1Ep02.setGetHintTime(LocalDateTime.now());
////            }
//            hint = hintInfo.get().getHint();
//            image = hintInfo.get().getHintImage();
//        } else if (quizInfo.getPlaceCode() == 2) {
////            Quiz2Ep02 quiz2Ep02 = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user)
////                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//            Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                    quizInfo.getQuizNumber(), hintNumber);
//            if (hintInfo.isEmpty()) {
//                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//            }
//
////            if (checkAlreadySolvedQuiz.getAnswer().isEmpty()) {
////                if (hintNumber == 1) {
////                    quiz2Ep02.setFirstHintTime(LocalDateTime.now());
////                } else if (hintNumber == 2) {
////                    quiz2Ep02.setSecondHintTime(LocalDateTime.now());
////                }
////            }
//            hint = hintInfo.get().getHint();
//            image = hintInfo.get().getHintImage();
//        } else if (quizInfo.getPlaceCode() == 3) {
////            Quiz3Ep02 quiz3Ep02 = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user)
////                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//            Optional<HintEp02> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
//                    quizInfo.getQuizNumber(), hintNumber);
//            if (hintInfo.isEmpty()) {
//                throw new BusinessLogicException(ExceptionCode.HINT_NOT_FOUND);
//            }
//
////                if (hintNumber == 1) {
////                    quiz3Ep02.setFirstGetHintTime(LocalDateTime.now());
////                } else if (hintNumber == 2) {
////                    quiz3Ep02.setSecondGetHintTime(LocalDateTime.now());
////                }
////            if (checkAlreadySolvedQuiz.getAnswer().isEmpty()) {
////                quiz3Ep02.setGetHintTime(LocalDateTime.now());
////            }
//            hint = hintInfo.get().getHint();
//            image = hintInfo.get().getHintImage();
//        } else {
//            throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
//        }

        return new HintDto(hint, image);
    }
}
