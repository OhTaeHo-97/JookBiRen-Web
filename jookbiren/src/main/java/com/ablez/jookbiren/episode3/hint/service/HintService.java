package com.ablez.jookbiren.episode3.hint.service;

import com.ablez.jookbiren.episode3.hint.entity.HintEp03;
import com.ablez.jookbiren.episode3.hint.repository.HintRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HintService {
    private final HintRepository hintRepository;

    public Optional<HintEp03> getHintByQuizAndHintNumber(int placeCode, int quizNumber, int hintNumber) {
        return hintRepository.findByQuizAndHintNumber(placeCode, quizNumber, hintNumber);
    }
}
