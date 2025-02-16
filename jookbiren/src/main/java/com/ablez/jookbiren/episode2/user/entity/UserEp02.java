package com.ablez.jookbiren.episode2.user.entity;

import com.ablez.jookbiren.episode2.quiz.entity.Quiz0Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz1Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz2Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.Quiz3Ep02;
import com.ablez.jookbiren.episode2.quiz.entity.WrongAnswerEp02;
import com.ablez.jookbiren.userInfo.entity.UserInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class UserEp02 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Setter
    private Integer answerCount = 0;
    @Setter
    private Integer answerStatusCode = 0;
    private Integer criminal1 = 0;
    private Integer criminal2 = 0;
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

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Quiz0Ep02> quiz0s = new ArrayList<>();
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Quiz1Ep02> quiz1s = new ArrayList<>();
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Quiz2Ep02> quiz2s = new ArrayList<>();
    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Quiz3Ep02 quiz3;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WrongAnswerEp02> wrongAnswers = new ArrayList<>();

    public UserEp02(String code) {
        this.code = code;
    }

    public void updateCriminal1(int criminal1) {
        this.criminal1 = criminal1;
    }

    public void updateCriminal2(int criminal2) {
        this.criminal2 = criminal2;
    }

    public void addQuiz0(Quiz0Ep02 quiz0) {
        this.quiz0s.add(quiz0);
    }

    public void addQuiz1(Quiz1Ep02 quiz1) {
        this.quiz1s.add(quiz1);
    }

    public void addQuiz2(Quiz2Ep02 quiz2) {
        this.quiz2s.add(quiz2);
    }

    public void setQuiz3(Quiz3Ep02 quiz3) {
        this.quiz3 = quiz3;
    }

    public void updateFirstLoginTime() {
        if (this.firstLoginTime == null) {
            firstLoginTime = LocalDateTime.now();
        }
    }
}
