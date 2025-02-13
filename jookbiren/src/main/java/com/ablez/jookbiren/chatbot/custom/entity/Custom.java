package com.ablez.jookbiren.chatbot.custom.entity;

import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.EYES;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.NECKLACE;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.POSE;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.SNOUT;
import static com.ablez.jookbiren.chatbot.custom.utils.CustomConstant.SUNGLASSES;

import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.Custom1To2Dto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.Custom3To4Dto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.Custom4To5Dto;
import com.ablez.jookbiren.chatbot.custom.dto.CustomDto.CustomImageDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Custom {
    private String category;
    private String type;
    private int value;

    private Custom(String category, String type) {
        this.category = category;
        this.type = type;
    }

    public static List<Custom> customImageDtoToCustoms(CustomImageDto customImageInfo) {
        List<Custom> customs = new ArrayList<>();
        customs.add(new Custom(EYES, customImageInfo.getAction().getParams().getInput_eyes()));
        customs.add(new Custom(SUNGLASSES, customImageInfo.getAction().getParams().getInput_sunglasses()));
        customs.add(new Custom(SNOUT, customImageInfo.getAction().getParams().getInput_snout()));
        customs.add(new Custom(NECKLACE, customImageInfo.getAction().getParams().getInput_necklace()));
        customs.add(new Custom(POSE, customImageInfo.getAction().getParams().getPose()));
        return customs;
    }

    public static Custom custom1To2DtoToCustom(Custom1To2Dto custom1To2Dto) {
        return new Custom(EYES, custom1To2Dto.getAction().getParams().getEyes());
    }

    public static Custom custom3To4DtoToCustom(Custom3To4Dto custom3To4Dto) {
        return new Custom(SNOUT, custom3To4Dto.getAction().getParams().getSnout());
    }

    public static Custom custom4To5DtoToCustom(Custom4To5Dto custom4To5Dto) {
        return new Custom(NECKLACE, custom4To5Dto.getAction().getParams().getNecklace());
    }
}
