package com.ablez.jookbiren.chatbot.answer.utils;

import com.ablez.jookbiren.chatbot.quiz.entity.Quiz;
import java.util.HashMap;
import java.util.Map;

public class AnswerConstant {
    public static final Map<Quiz, String> ANSWERS = new HashMap<>() {{
        put(new Quiz(0, 0), "ablez");
        put(new Quiz(1, 1), "qk-3.517");
        put(new Quiz(1, 2), "불량품");
        put(new Quiz(1, 3), "환풍구");
        put(new Quiz(1, 4), "exit");
        put(new Quiz(1, 5), "free");
        put(new Quiz(1, 6), "free");
        put(new Quiz(1, 7), "free");
        put(new Quiz(2, 1), "find");
        put(new Quiz(2, 2), "20:20");
        put(new Quiz(2, 3), "charge");
        put(new Quiz(2, 4), "conan");
        put(new Quiz(3, 1), "d");
        put(new Quiz(3, 2), "훼손");
        put(new Quiz(3, 3), "sew");
    }};
}
