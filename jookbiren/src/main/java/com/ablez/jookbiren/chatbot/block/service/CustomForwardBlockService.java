package com.ablez.jookbiren.chatbot.block.service;

import static com.ablez.jookbiren.chatbot.block.utils.CustomForwardBlock.CUSTOM_FORWARD_BLOCKS;

import com.ablez.jookbiren.chatbot.dto.CustomForwardBlockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomForwardBlockService {
    public String findBlockId(String category, boolean isPossible) {
        String blockId = validateCustomForwardBlock(category, isPossible);
        if (blockId == null) {
            return "";
        }
        return blockId;
    }

    private String validateCustomForwardBlock(String category, boolean isPossible) {
        return CUSTOM_FORWARD_BLOCKS.get(new CustomForwardBlockDto(category, isPossible));
    }
}
