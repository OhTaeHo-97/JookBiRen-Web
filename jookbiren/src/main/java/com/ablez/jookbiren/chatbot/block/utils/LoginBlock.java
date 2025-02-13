package com.ablez.jookbiren.chatbot.block.utils;

import com.ablez.jookbiren.chatbot.dto.LoginBlockDto;
import java.util.HashMap;
import java.util.Map;

public class LoginBlock {
    public static final Map<LoginBlockDto, String> LOGIN_BLOCK = new HashMap<>() {{
        put(new LoginBlockDto(true, false, true), "651ed0ed4dff0f561d1a03c3");
        put(new LoginBlockDto(true, true, true), "6539e61bbe6c65335ac557cd");
        put(new LoginBlockDto(false, false, true), "64fee1bc6a34bd19e09017f1");
        put(new LoginBlockDto(true, false, false), "652fc1bad134aa37160bdb6f");
        put(new LoginBlockDto(false, false, false), "6527df96fbc3a30246698ecb");
    }};
}
