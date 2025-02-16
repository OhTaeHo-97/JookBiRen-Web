package com.ablez.jookbiren.episode1.quiz.service;

import com.ablez.jookbiren.episode1.answer.dto.AnswerDto.FindAnswerResponseDto;
import com.ablez.jookbiren.episode1.answer.service.AnswerService;
import com.ablez.jookbiren.episode1.dto.JookbirenDto.Quiz;
import com.ablez.jookbiren.episode1.hint.entity.HintEp01;
import com.ablez.jookbiren.episode1.hint.service.HintService;
import com.ablez.jookbiren.episode1.quiz.dto.QuizDto.HintDto;
import com.ablez.jookbiren.episode1.quiz.dto.QuizDto.PageDto;
import com.ablez.jookbiren.episode1.quiz.dto.QuizDto.QuizPageDto;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz0Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz1Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz2Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz3Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz4Ep01;
import com.ablez.jookbiren.episode1.user.entity.UserEp01;
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
public class QuizService {
    private final AnswerService answerService;
    private final QuizInfoService quizInfoService;
    private final HintService hintService;

    public PageDto getCurrentSituationAndSolvedProblems(int placeCode, UserEp01 user) {
        if (placeCode == 0) {
            return getCurrentSituationAndAngukSolvedProblems(user);
        } else if (placeCode == 1) {
            return getCurrentSituationAndChangdeokgungSolvedProblems(user);
        } else if (placeCode == 2) {
            return getCurrentSituationAndBukchonSolvedProblems(user);
        } else if (placeCode == 3) {
            return getCurrentSituationAndSsamzigilSolvedProblems(user);
        } else if (placeCode == 4) {
            return getCurrentSituationAndGangNamSolvedProblems(user);
        }

        throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
    }

    private PageDto getCurrentSituationAndGangNamSolvedProblems(UserEp01 user) {
        List<Quiz4Ep01> solvedQuizzes = quizInfoService.getGangnamSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndSsamzigilSolvedProblems(UserEp01 user) {
        List<Quiz3Ep01> solvedQuizzes = quizInfoService.getSsamzigilSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndBukchonSolvedProblems(UserEp01 user) {
        List<Quiz2Ep01> solvedQuizzes = quizInfoService.getBukchonSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndChangdeokgungSolvedProblems(UserEp01 user) {
        List<Quiz1Ep01> solvedQuizzes = quizInfoService.getChangdeokgungSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    private PageDto getCurrentSituationAndAngukSolvedProblems(UserEp01 user) {
        List<Quiz0Ep01> solvedQuizzes = quizInfoService.getAngukSolvedQuizzes(user);
        List<Integer> quizNumbers = solvedQuizzes.stream().map(quiz -> quiz.getQuiz().getQuizNumber())
                .collect(Collectors.toList());
        return new PageDto(user.getSolvedQuizCount(), user.getAnswerCount(), quizNumbers);
    }

    public QuizPageDto checkAlreadySolvedQuiz(UserEp01 user, Quiz quizInfo) {
        if (quizInfo.getPlaceCode() == 0) {
            return findAnswerOfAngukStation(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 1) {
            return findAnswerOfChangdeokgung(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 2) {
            return findAnswerOfBukchon(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 3) {
            return findAnswerOfSsamzigil(user, quizInfo);
        } else if (quizInfo.getPlaceCode() == 4) {
            return findAnswerOfGangnam(user, quizInfo);
        } else {
            throw new BusinessLogicException(ExceptionCode.QUIZ_NOT_FOUND); // 잘못된 퀴즈 정보
        }
    }

    private QuizPageDto findAnswerOfGangnam(UserEp01 user, Quiz quizInfo) {
        Quiz4Ep01 quiz = quizInfoService.findByQuizNumberAndUser4(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz4Ep01 newQuiz = quizInfoService.insertQuiz4(quizInfo.getQuizNumber(), user);
            user.setQuiz4s(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    private QuizPageDto findAnswerOfSsamzigil(UserEp01 user, Quiz quizInfo) {
        Quiz3Ep01 quiz = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz3Ep01 newQuiz = quizInfoService.insertQuiz3(quizInfo.getQuizNumber(), user);
            user.addQuiz3(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    private QuizPageDto findAnswerOfBukchon(UserEp01 user, Quiz quizInfo) {
        Quiz2Ep01 quiz = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz2Ep01 newQuiz = quizInfoService.insertQuiz2(quizInfo.getQuizNumber(), user);
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

    private QuizPageDto findAnswerOfChangdeokgung(UserEp01 user, Quiz quizInfo) {
        Quiz1Ep01 quiz = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz1Ep01 newQuiz = quizInfoService.insertQuiz1(quizInfo.getQuizNumber(), user);
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

    private QuizPageDto findAnswerOfAngukStation(UserEp01 user, Quiz quizInfo) {
        Quiz0Ep01 quiz = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user).orElse(null);
        if (quiz == null) {
            Quiz0Ep01 newQuiz = quizInfoService.insertQuiz0(quizInfo.getQuizNumber(), user);
            user.setQuiz0s(newQuiz);
            return new QuizPageDto();
        } else if (quiz.getFirstAnswerTime() == null) {
            return new QuizPageDto();
        } else {
            FindAnswerResponseDto answer = answerService.findAnswer(
                    new Quiz(quiz.getQuiz().getPlaceCode(), quiz.getQuiz().getQuizNumber()));
            return new QuizPageDto(answer.getAnswer());
        }
    }

    public HintDto findHint(int hintNumber, UserEp01 user, Quiz quizInfo) {
        String hint = null;
        String image = null;

        if (quizInfo.getPlaceCode() == 0) {
            Quiz0Ep01 log = quizInfoService.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz0(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp01> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
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
        } else if (quizInfo.getPlaceCode() == 1) {
            Quiz1Ep01 log = quizInfoService.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz1(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp01> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
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
        } else if (quizInfo.getPlaceCode() == 2) {
            Quiz2Ep01 log = quizInfoService.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz2(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp01> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
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
            Quiz3Ep01 log = quizInfoService.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz3(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp01> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
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
        } else if (quizInfo.getPlaceCode() == 4) {
            Quiz4Ep01 log = quizInfoService.findByQuizNumberAndUser4(quizInfo.getQuizNumber(), user).orElse(null);
            if (log == null) {
                log = quizInfoService.insertQuiz4(quizInfo.getQuizNumber(), user);
            }

            if (log.getFirstAnswerTime() == null) {
                Optional<HintEp01> hintInfo = hintService.getHintByQuizAndHintNumber(quizInfo.getPlaceCode(),
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

        return new HintDto(hint, image);

//        QuizPageDto checkAlreadySolvedQuiz = checkAlreadySolvedQuiz(user, quizInfo);
//
//        if (checkAlreadySolvedQuiz.getAnswer().isEmpty()) {
//            if (quizInfo.getPlaceCode() == 0) {
//                Quiz0Ep01 quiz0Ep01 = quizRepository.findByQuizNumberAndUser0(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                if (quiz0Ep01.getGetHintTime() == null) {
//                    quiz0Ep01.setGetHintTime(LocalDateTime.now());
//                }
//            } else if (quizInfo.getPlaceCode() == 1) {
//                Quiz1Ep01 quiz1Ep01 = quizRepository.findByQuizNumberAndUser1(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                if (quiz1Ep01.getGetHintTime() == null) {
//                    quiz1Ep01.setGetHintTime(LocalDateTime.now());
//                }
//            } else if (quizInfo.getPlaceCode() == 2) {
//                Quiz2Ep01 quiz2Ep01 = quizRepository.findByQuizNumberAndUser2(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                if (quiz2Ep01.getGetHintTime() == null) {
//                    quiz2Ep01.setGetHintTime(LocalDateTime.now());
//                }
//            } else if (quizInfo.getPlaceCode() == 3) {
//                Quiz3Ep01 quiz3Ep01 = quizRepository.findByQuizNumberAndUser3(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                if (quiz3Ep01.getGetHintTime() == null) {
//                    quiz3Ep01.setGetHintTime(LocalDateTime.now());
//                }
//            } else if (quizInfo.getPlaceCode() == 4) {
//                Quiz4Ep01 quiz4Ep01 = quizRepository.findByQuizNumberAndUser4(quizInfo.getQuizNumber(), user)
//                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUIZ_HISTORY_NOT_FOUND));
//                if (quiz4Ep01.getGetHintTime() == null) {
//                    quiz4Ep01.setGetHintTime(LocalDateTime.now());
//                }
//            } else {
//                throw new BusinessLogicException(ExceptionCode.INVALID_PLACE_CODE);
//            }
//        }
//
//        return new HintDto(checkAlreadySolvedQuiz.getAnswer().isEmpty());
    }
}
