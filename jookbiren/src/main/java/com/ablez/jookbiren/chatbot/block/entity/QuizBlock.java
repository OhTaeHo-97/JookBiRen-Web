package com.ablez.jookbiren.chatbot.block.entity;

import lombok.Getter;

@Getter
public class QuizBlock {
    private int episode;
    private int quizNumber;
    private boolean isCorrect;
    private boolean isAnswer;
    private String blockId;
}
