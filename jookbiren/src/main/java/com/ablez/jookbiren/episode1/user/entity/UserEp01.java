package com.ablez.jookbiren.episode1.user.entity;

import com.ablez.jookbiren.episode1.quiz.entity.Quiz0Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz1Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz2Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz3Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.Quiz4Ep01;
import com.ablez.jookbiren.episode1.quiz.entity.WrongAnswerEp01;
import com.ablez.jookbiren.userInfo.entity.UserInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
public class UserEp01 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Setter
    private Integer answerCount = 0;
    @Setter
    private Integer answerStatusCode = 0;
    private Integer criminal = 0;
    @Setter
    private Integer score = 0;
    @Setter
    private Integer solvedQuizCount = 0;
    @Setter
    private String accessToken;
    @Column(nullable = false)
    private String code;
    @Setter
    private LocalDateTime answerTime;
    private LocalDateTime firstLoginTime;

    @OneToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Quiz0Ep01 quiz0s;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz1Ep01> quiz1s = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz2Ep01> quiz2s = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz3Ep01> quiz3s = new ArrayList<>();

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Quiz4Ep01 quiz4s;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WrongAnswerEp01> wrongAnswers = new ArrayList<>();

    public UserEp01(String code) {
        this.code = code;
    }

    public void updateCriminal(int criminal) {
        this.criminal = criminal;
    }

    public void updateFirstLoginTime() {
        if (this.firstLoginTime == null) {
            firstLoginTime = LocalDateTime.now();
        }
    }

    public void addQuiz1(Quiz1Ep01 quiz1Ep01) {
        this.quiz1s.add(quiz1Ep01);
    }

    public void addQuiz2(Quiz2Ep01 quiz2Ep01) {
        this.quiz2s.add(quiz2Ep01);
    }

    public void addQuiz3(Quiz3Ep01 quiz3Ep01) {
        this.quiz3s.add(quiz3Ep01);
    }
}
