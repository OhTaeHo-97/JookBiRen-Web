package com.ablez.jookbiren.chatbot.block.utils;

import com.ablez.jookbiren.chatbot.dto.CustomForwardBlockDto;
import java.util.HashMap;
import java.util.Map;

public class CustomForwardBlock {
    public static final Map<CustomForwardBlockDto, String> CUSTOM_FORWARD_BLOCKS = new HashMap<>() {{
        put(new CustomForwardBlockDto("eyes", true), "653005a945c2dc11a473a41e");
        put(new CustomForwardBlockDto("eyes", false), "653006e0d75b35342b2d785e");
        put(new CustomForwardBlockDto("snout", true), "652aed6e648eee21606e2ae7");
        put(new CustomForwardBlockDto("snout", false), "652fc35865e4e7593e59b233");
        put(new CustomForwardBlockDto("necklace", true), "653005b8291659627339b73f");
        put(new CustomForwardBlockDto("necklace", false), "65300908291659627339b754");
    }};
}
