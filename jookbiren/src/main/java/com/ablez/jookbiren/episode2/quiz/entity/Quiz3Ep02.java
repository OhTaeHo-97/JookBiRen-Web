package com.ablez.jookbiren.episode2.quiz.entity;

import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
public class Quiz3Ep02 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quiz3Id;
    private Integer quizNumber;
    @Setter
    private LocalDateTime firstAccessTime;
    @Setter
    private LocalDateTime firstAnswerTime;
    @Setter
    private LocalDateTime getHintTime;
    @Setter
    private LocalDateTime getAnswerTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEp02 userId;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEp02 quiz;

    public Quiz3Ep02(Integer quizNumber, UserEp02 userId, QuizEp02 quiz) {
        this.quizNumber = quizNumber;
        this.firstAccessTime = LocalDateTime.now();
        this.userId = userId;
        this.quiz = quiz;
    }
}
