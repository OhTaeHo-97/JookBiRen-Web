package com.ablez.jookbiren.chatbot.custom.utils;

import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.EYES;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.NECKLACE;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.POSE;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.SNOUT;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.SUNGLASSES;

import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.CustomInfoDto;
import java.util.HashMap;
import java.util.Map;

public class Custom {
    public static final Map<CustomInfoDto, Integer> CUSTOM_INFO = new HashMap<>() {{
        put(new CustomInfoDto(EYES, "동글"), 1);
        put(new CustomInfoDto(EYES, "윙크"), 2);
        put(new CustomInfoDto(EYES, "당황"), 3);
        put(new CustomInfoDto(EYES, "초롱"), 4);
        put(new CustomInfoDto(SUNGLASSES, "없음"), 1);
        put(new CustomInfoDto(SUNGLASSES, "왹져"), 3);
        put(new CustomInfoDto(SUNGLASSES, "픽셀"), 4);
        put(new CustomInfoDto(SNOUT, "미소"), 1);
        put(new CustomInfoDto(SNOUT, "메롱"), 2);
        put(new CustomInfoDto(SNOUT, "놀란"), 3);
        put(new CustomInfoDto(SNOUT, "뽀뽀"), 4);
        put(new CustomInfoDto(NECKLACE, "나비넥타이"), 1);
        put(new CustomInfoDto(NECKLACE, "넥타이"), 2);
        put(new CustomInfoDto(NECKLACE, "스카프"), 3);
        put(new CustomInfoDto(NECKLACE, "방울"), 4);
        put(new CustomInfoDto(POSE, "차렷"), 1);
        put(new CustomInfoDto(POSE, "만세"), 2);
        put(new CustomInfoDto(POSE, "어머"), 3);
        put(new CustomInfoDto(POSE, "브이"), 4);
    }};
}
