package com.ablez.jookbiren.chatbot.answer.service;

import static com.ablez.jookbiren.chatbot.answer.utils.AnswerConstant.ANSWERS;
import static com.ablez.jookbiren.chatbot.block.utils.QuizBlock.QUIZ_BLOCKS;

import com.ablez.jookbiren.chatbot.answer.dto.AnswerDto.ResponseDto;
import com.ablez.jookbiren.chatbot.answer.entity.Answer;
import com.ablez.jookbiren.chatbot.dto.QuizBlockDto;
import com.ablez.jookbiren.chatbot.quiz.entity.Quiz;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatbotAnswerService {
    private static final Set<String> POSSIBLE_ANSWERS_11 = new HashSet<>() {{
        add("qk3517");
        add("qk3.517");
        add("3.517");
        add("3517");
    }};
    private static final Set<String> POSSIBLE_ANSWERS_22 = new HashSet<>() {{
        add("20시20분");
        add("20시 20분");
        add("20: 20");
        add("20 :20");
        add("20 : 20");
        add("2020");
        add("20 20");
        add("오후8시20분");
        add("오후 8시20분");
        add("오후 8시 20분");
        add("오후8시 20분");
    }};

    public ResponseDto checkAnswer(Answer userAnswer) {
        String validatedAnswer = validate(userAnswer);
        boolean isCorrect = validateCorrectAnswer(userAnswer, validatedAnswer);
        Answer answer = null;
        if (!isCorrect) {
            answer = new Answer(userAnswer.getEpisode(), userAnswer.getQuizNumber(), null);
        } else {
            answer = new Answer(userAnswer.getEpisode(), userAnswer.getQuizNumber(), validatedAnswer);
        }

        String blockId = getBlockId(answer);
        String answerBlockId = getAnswerBlockId(answer);

        return Answer.answerToResponseDto(answer, blockId, answerBlockId,
                (userAnswer.getEpisode() == 0 && userAnswer.getQuizNumber() == 0));
    }

    private String getBlockId(Answer answer) {
        if (answer.getAnswer() == null) {
            return QUIZ_BLOCKS.get(new QuizBlockDto(answer.getEpisode(), answer.getQuizNumber(), false, false));
        }
        return QUIZ_BLOCKS.get(new QuizBlockDto(answer.getEpisode(), answer.getQuizNumber(), true, false));
    }

    private String getAnswerBlockId(Answer answer) {
        return QUIZ_BLOCKS.get(new QuizBlockDto(answer.getEpisode(), answer.getQuizNumber(), true, true));
    }

    private boolean validateCorrectAnswer(Answer userAnswer, String answer) {
        Quiz quiz = new Quiz(userAnswer.getEpisode(), userAnswer.getQuizNumber());
        String correctAnswer = ANSWERS.get(quiz);
        return answer.equals(correctAnswer);
    }

    private String validate(Answer answer) {
        if (answer.getEpisode() == 1 && answer.getQuizNumber() == 1) {
            if (POSSIBLE_ANSWERS_11.contains(answer.getAnswer())) {
                return "qk-3.517";
            }
        }

        if (answer.getEpisode() == 2 && answer.getQuizNumber() == 2) {
            if (POSSIBLE_ANSWERS_22.contains(answer.getAnswer())) {
                return "20:20";
            }
        }

        return answer.getAnswer();
    }
}
