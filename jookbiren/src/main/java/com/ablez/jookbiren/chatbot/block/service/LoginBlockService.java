package com.ablez.jookbiren.chatbot.block.service;

import static com.ablez.jookbiren.chatbot.block.utils.LoginBlock.LOGIN_BLOCK;

import com.ablez.jookbiren.chatbot.block.entity.LoginBlock;
import com.ablez.jookbiren.chatbot.dto.LoginBlockDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoginBlockService {
    public List<String> findLoginBlockByLogin(boolean isSuccessfulLogin, boolean isStory) {
        return getLoginBlocks(isSuccessfulLogin, isStory);
    }

    private List<String> getLoginBlocks(boolean isSuccessfulLogin, boolean isStory) {
        if (isSuccessfulLogin && isStory) {
            return getSuccessfulLoginBlocks();
        }
        if (isSuccessfulLogin && !isStory) {
            return getSuccessfulCustomLoginBlocks();
        }
        if (!isSuccessfulLogin && !isStory) {
            return getUnsuccessfulCustomLoginBlocks();
        }
        return getUnsuccessfulLoginBlocks();
    }

    private List<String> getSuccessfulLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        String loginBlock = LOGIN_BLOCK.get(new LoginBlockDto(true, false, true));
        String tutorialLoginBlock = LOGIN_BLOCK.get(new LoginBlockDto(true, true, true));
        loginBlocks.add(loginBlock);
        loginBlocks.add(tutorialLoginBlock);
        return loginBlocks;
    }

    private List<String> getSuccessfulCustomLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        loginBlocks.add(LOGIN_BLOCK.get(new LoginBlockDto(true, false, false)));
        return loginBlocks;
    }

    private List<String> getUnsuccessfulLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        loginBlocks.add(LOGIN_BLOCK.get(new LoginBlockDto(false, false, true)));
        return loginBlocks;
    }

    private List<String> getUnsuccessfulCustomLoginBlocks() {
        List<String> loginBlocks = new ArrayList<>();
        loginBlocks.add(LOGIN_BLOCK.get(new LoginBlockDto(false, false, false)));
        return loginBlocks;
    }

    private List<String> extractBlockIdInLoginBlocks(List<LoginBlock> loginBlocks) {
        System.out.println(loginBlocks);
        return loginBlocks.stream().map(loginBlock -> loginBlock.getBlockId()).collect(Collectors.toList());
    }
}
