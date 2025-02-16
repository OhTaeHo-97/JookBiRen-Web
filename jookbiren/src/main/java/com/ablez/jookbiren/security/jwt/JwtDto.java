package com.ablez.jookbiren.security.jwt;

import com.ablez.jookbiren.security.utils.JwtHeaderUtilEnums;
import lombok.Getter;

public class JwtDto {
    @Getter
    public static class TokenDto {
        private String accessToken;

        public TokenDto(String accessToken) {
            this.accessToken = JwtHeaderUtilEnums.GRANT_TYPE.getValue() + accessToken;
        }
    }
}
