package com.ablez.jookbiren.episode2.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

public class JookBiRenDto {
    @Getter
    public static class Quiz {
        private int placeCode;
        private int quizNumber;

        public Quiz(String quizInfo) {
            placeCode = quizInfo.charAt(0) - '0';
            quizNumber = Integer.parseInt(quizInfo.substring(1));
        }

        public Quiz(int placeCode, int quizNumber) {
            this.placeCode = placeCode;
            this.quizNumber = quizNumber;
        }
    }

    // 페이지네이션된 다수 데이터 응답 DTO
    @Getter
    public static class MultipleResponseDto<T> {
        private List<T> data;
        private PageInfo pageInfo;

        public MultipleResponseDto(List<T> data, Page page) {
            this.data = data;
            this.pageInfo = new PageInfo(page.getNumber() + 1, page.getSize(), page.getTotalElements(),
                    page.getTotalPages());
        }
    }
}
