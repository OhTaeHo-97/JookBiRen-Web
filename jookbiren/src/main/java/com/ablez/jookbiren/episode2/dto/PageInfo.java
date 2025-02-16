package com.ablez.jookbiren.episode2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 페이지네이션 진행시 페이지 정보를 나타내는 DTO
@Getter
@AllArgsConstructor
public class PageInfo {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
