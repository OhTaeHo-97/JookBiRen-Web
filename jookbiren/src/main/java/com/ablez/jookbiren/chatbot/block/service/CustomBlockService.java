package com.ablez.jookbiren.chatbot.block.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomBlockService {
    public String findCustomBlockId(boolean isInitial) {
        if (isInitial) {
            return "6762dd757c842c4d39423b2c";
        }
        return "652fc1bad134aa37160bdb6f";
    }
}
