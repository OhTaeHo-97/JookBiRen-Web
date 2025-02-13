package com.ablez.jookbiren.chatbot.custom.utils;

import java.util.HashMap;
import java.util.Map;

public class CustomConstant {
    public static final String EYES = "eyes";
    public static final String SUNGLASSES = "sunglasses";
    public static final String SNOUT = "snout";
    public static final String NECKLACE = "necklace";
    public static final String POSE = "pose";

    public static final Map<String, Integer> CUSTOM_CODE_INDEX = new HashMap<>() {{
        put(EYES, 2);
        put(SNOUT, 1);
        put(NECKLACE, 0);
    }};
}
