package com.ablez.jookbiren.episode2.hint.service;

import com.ablez.jookbiren.episode2.hint.entity.HintEp02;
import com.ablez.jookbiren.episode2.hint.repository.Ep2HintRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class Ep2HintService {
    private final Ep2HintRepository hintRepository;

    public Optional<HintEp02> getHintByQuizAndHintNumber(int placeCode, int quizNumber, int hintNumber) {
        return hintRepository.findByQuizAndHintNumber(placeCode, quizNumber, hintNumber);
    }

    public List<HintEp02> getHintByQuiz(int placeCode, int quizNumber) {
        return hintRepository.findAllByQuiz(placeCode, quizNumber);
    }
}
