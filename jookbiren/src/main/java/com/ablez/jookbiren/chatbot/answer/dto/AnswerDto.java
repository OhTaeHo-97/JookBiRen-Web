package com.ablez.jookbiren.chatbot.answer.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class AnswerDto {
    private static final String VERSION = "2.0";
    private static final String ACTION = "block";

    @Getter
    public static class AnswerPostDto {
        private Parameter action;
        private Block userRequest;
    }

    @Getter
    public static class QuizInfo {
        private String sys_text;
    }

    @Getter
    public static class Parameter {
        private QuizInfo params;
    }

    @Getter
    public static class BlockName {
        private String name;
    }

    @Getter
    public static class Block {
        private BlockName block;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ResponseDto {
        private final String version = VERSION;
        private final Template template;
    }

    @Getter
    @AllArgsConstructor
    public static class Template {
        private final List<Output> outputs;
    }

    @Getter
    public static class Output {
        private final TextCard textCard;

        private Output(TextCard textCards) {
            this.textCard = textCards;
        }

        public static Output of(TextCard textCards) {
            return new Output(textCards);
        }
    }

    @Getter
    public static class TextCard {
        private final String text;
        private final List<Button> buttons;

        private TextCard(String text, List<Button> buttons) {
            this.text = text;
            this.buttons = buttons;
        }

        public static TextCard of(String text, List<Button> buttons) {
            return new TextCard(text, buttons);
        }
    }

    @Getter
    public static class Button {
        private final String action = ACTION;
        private final String label;
        private final String blockId;

        private Button(String label, String blockId) {
            this.label = label;
            this.blockId = blockId;
        }

        public static Button of(String label, String blockId) {
            return new Button(label, blockId);
        }
    }
}
