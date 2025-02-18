package com.ablez.jookbiren.episode1.hint.service;

import com.ablez.jookbiren.episode1.hint.entity.HintEp01;
import com.ablez.jookbiren.episode1.hint.repository.Ep1HintRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class Ep1HintService {
    private final Ep1HintRepository hintRepository;

    public Optional<HintEp01> getHintByQuizAndHintNumber(int placeCode, int quizNumber, int hintNumber) {
        return hintRepository.findByQuizAndHintNumber(placeCode, quizNumber, hintNumber);
    }
}
