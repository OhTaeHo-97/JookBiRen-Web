package com.ablez.jookbiren.chatbot.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserDto {
    private static final String VERSION = "2.0";
    private static final String ACTION = "block";

    @Getter
    public static class UserPostDto {
        private Parameter action;
        private UserInfo userRequest;
    }

    @Getter
    public static class Code {
        private String input_pw;
    }

    @Getter
    public static class Parameter {
        private Code params;
    }

    @Getter
    public static class KakaoId {
        private String id;
    }

    @Getter
    public static class UserInfo {
        private KakaoId user;
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
        private final TextCardResponse textCard;

        private Output(TextCardResponse textCard) {
            this.textCard = textCard;
        }

        public static Output of(TextCardResponse textCard) {
            return new Output(textCard);
        }
    }

    public interface TextCardResponse {
        String getText();
    }

    @Getter
    public static class TextCard implements TextCardResponse {
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
    @AllArgsConstructor
    public static class BannedTextCard implements TextCardResponse {
        private final String text;
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
