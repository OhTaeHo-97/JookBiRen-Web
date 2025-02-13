package com.ablez.jookbiren.chatbot.custom.dto;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CustomDto {
    private static final String ACTION = "block";
    private static final String VERSION = "2.0";
    private static final String NOT_EXIST_CODE = "는 없는 접속코드 입니다.\n다시 입력해 주세요.";
    private static final String ALT_TEXT = "커스텀입니다";
    private static final String TEXT = "%s - %s 을(를) 선택했습니다.";

    @Getter
    public static class CustomOpenDto {
        private User userRequest;
    }

    @Getter
    public static class CustomImageDto {
        private Action action;
    }

    @Getter
    public static class Custom1To2Dto {
        private User userRequest;
        private Custom1To2Action action;
    }

    @Getter
    public static class Custom3To4Dto {
        private User userRequest;
        private Custom3To4Action action;
    }

    @Getter
    public static class Custom4To5Dto {
        private User userRequest;
        private Custom4To5Action action;
    }

    @Getter
    public static class Action {
        private Params params;
    }

    @Getter
    public static class Params {
        private String input_eyes;
        private String input_sunglasses;
        private String input_snout;
        private String input_necklace;
        private String pose;
    }

    @Getter
    public static class Custom1To2Action {
        private Eyes params;
    }

    @Getter
    public static class Custom3To4Action {
        private Snout params;
    }

    @Getter
    public static class Custom4To5Action {
        private Necklace params;
    }

    @Getter
    public static class Eyes {
        private String eyes;
    }

    @Getter
    public static class Snout {
        private String snout;
    }

    @Getter
    public static class Necklace {
        private String necklace;
    }

    @Getter
    public static class User {
        private KakaoId user;
    }

    @Getter
    public static class KakaoId {
        private String id;
    }

    @Getter
    public static class ResponseDto {
        private String version = VERSION;
        private Template template;

        public ResponseDto(Template template) {
            this.template = template;
        }
    }

    @Getter
    public static class Template {
        private List<Output> outputs;

        public Template(List<Output> outputs) {
            this.outputs = outputs;
        }
    }

    @Getter
    public static class Output {
        private TextCard textCard;

        public Output(TextCard textCard) {
            this.textCard = textCard;
        }
    }

    @Getter
    public static class TextCard {
        private String text = NOT_EXIST_CODE;
    }

    @Getter
    public static class CustomImageResponseDto {
        private String version = VERSION;
        private ImageTemplate template;

        public CustomImageResponseDto(ImageTemplate template) {
            this.template = template;
        }
    }

    @Getter
    public static class ImageTemplate {
        private List<ImageOutput> outputs;
        private List<QuickReply> quickReplies;

        public ImageTemplate(List<ImageOutput> outputs, List<QuickReply> quickReplies) {
            this.outputs = outputs;
            this.quickReplies = quickReplies;
        }
    }

    @Getter
    public static class ImageOutput {
        private SimpleImage simpleImage;

        public ImageOutput(SimpleImage simpleImage) {
            this.simpleImage = simpleImage;
        }
    }

    @Getter
    public static class SimpleImage {
        private String imageUrl;
        private String altText = ALT_TEXT;

        public SimpleImage(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    @Getter
    public static class QuickReply {
        private String messageText;
        private String action = ACTION;
        private String blockId;
        private String label;

        public QuickReply(String messageText, String blockId, String label) {
            this.messageText = messageText;
            this.blockId = blockId;
            this.label = label;
        }
    }

    @Getter
    public static class CustomAToBResponseDto {
        private String version = VERSION;
        private CustomAToBTemplate template;

        public CustomAToBResponseDto(CustomAToBTemplate template) {
            this.template = template;
        }
    }

    @Getter
    public static class CustomAToBTemplate {
        private List<CustomAToBOutput> outputs;

        public CustomAToBTemplate(List<CustomAToBOutput> outputs) {
            this.outputs = outputs;
        }
    }

    @Getter
    public static class CustomAToBOutput {
        private CustomAToBTextCard textCard;

        public CustomAToBOutput(CustomAToBTextCard textCard) {
            this.textCard = textCard;
        }
    }

    @Getter
    public static class CustomAToBTextCard {
        private String text;
        private List<CustomAToBButton> buttons;

        public CustomAToBTextCard(String category, String type, List<CustomAToBButton> buttons) {
            this.text = String.format(TEXT, category, type);
            this.buttons = buttons;
        }
    }

    @Getter
    public static class CustomAToBButton {
        private String action = ACTION;
        private String label;
        private String blockId;

        public CustomAToBButton(String label, String blockId) {
            this.label = label;
            this.blockId = blockId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CustomInfoDto {
        private String category;
        private String type;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CustomInfoDto that = (CustomInfoDto) o;
            return Objects.equals(category, that.category) && Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, type);
        }
    }
}
