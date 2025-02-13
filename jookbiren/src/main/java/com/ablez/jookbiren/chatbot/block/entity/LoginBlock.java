package com.ablez.jookbiren.chatbot.block.entity;

import lombok.Getter;

@Getter
public class LoginBlock {
    private String blockId;
    private boolean isSuccessfulLogin;
    private boolean isTutorial;
    private boolean isStory;
}
