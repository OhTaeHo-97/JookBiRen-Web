package com.ablez.jookbiren.episode2.quiz.entity;

import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class WrongAnswerEp02 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wrongAnswerId;
    private String answer;
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEp02 user;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEp02 quiz;

    public WrongAnswerEp02(String answer, LocalDateTime time, UserEp02 user, QuizEp02 quiz) {
        this.answer = answer;
        this.time = time;
        this.user = user;
        this.quiz = quiz;
    }
}
