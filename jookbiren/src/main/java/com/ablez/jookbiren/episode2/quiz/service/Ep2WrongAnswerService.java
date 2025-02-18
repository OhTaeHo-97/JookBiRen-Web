package com.ablez.jookbiren.episode2.quiz.service;

import com.ablez.jookbiren.episode2.quiz.entity.WrongAnswerEp02;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2WrongAnswerQuerydslRepository;
import com.ablez.jookbiren.episode2.quiz.repository.Ep2WrongAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class Ep2WrongAnswerService {
    private final Ep2WrongAnswerRepository wrongAnswerJpaRepository;
    private final Ep2WrongAnswerQuerydslRepository wrongAnswerRepository;
//    private final QuizInfoMapper quizInfoMapper;

    public void insertWrongAnswer(WrongAnswerEp02 wrongAnswer) {
        wrongAnswerJpaRepository.save(wrongAnswer);
    }

//    @Transactional(readOnly = true)
//    public List<WrongAnswerDto> getWrongAnswers() {
//        List<WrongAnswerEp02> wrongAnswers = wrongAnswerRepository.findAll();
//        return wrongAnswers.stream().map(quizInfoMapper::makeWrongAnswerDto).collect(Collectors.toList());
//    }
}
