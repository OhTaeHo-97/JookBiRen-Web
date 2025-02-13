package com.ablez.jookbiren.episode3.user.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class UserEp03 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Integer answerCount = 0;
    private Integer answerStatusCode = 0;
    private Integer criminal = 0;
    private Integer score = 0;
    private Integer solvedQuizCount = 0;
    private String accessToken;
    @Column(nullable = false)
    private String code;
    private LocalDateTime answerTime;
    private LocalDateTime firstLoginTime;
}
