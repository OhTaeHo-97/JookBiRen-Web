package com.ablez.jookbiren.episode2.user.entity;

import com.ablez.jookbiren.entity.UserInfo;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class UserEp02 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Integer answerCount = 0;
    private Integer answerStatusCode = 0;
    private Integer criminal1 = 0;
    private Integer criminal2 = 0;
    private Integer score = 0;
    private Integer solvedQuizCount = 0;
    private String accessToken;
    @Column(nullable = false)
    private String code;
    private LocalDateTime answerTime;
    private LocalDateTime firstLoginTime;

    @OneToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;
}
