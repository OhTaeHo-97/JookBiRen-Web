package com.ablez.jookbiren.episode3.user.entity;

import com.ablez.jookbiren.episode3.quiz.entity.Quiz0Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz1Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz2Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.Quiz3Ep03;
import com.ablez.jookbiren.episode3.quiz.entity.WrongAnswerEp03;
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
public class UserEp03 {
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

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<Quiz0Ep03> quiz0s = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz1Ep03> quiz1s = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz2Ep03> quiz2s = new ArrayList<>();

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Quiz3Ep03 quiz3s;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WrongAnswerEp03> wrongAnswers = new ArrayList<>();

    public UserEp03(String code) {
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

    public void addQuiz0(Quiz0Ep03 quiz0Ep03) {
        this.quiz0s.add(quiz0Ep03);
    }

    public void addQuiz1(Quiz1Ep03 quiz1Ep03) {
        this.quiz1s.add(quiz1Ep03);
    }

    public void addQuiz2(Quiz2Ep03 quiz2Ep03) {
        this.quiz2s.add(quiz2Ep03);
    }
}
