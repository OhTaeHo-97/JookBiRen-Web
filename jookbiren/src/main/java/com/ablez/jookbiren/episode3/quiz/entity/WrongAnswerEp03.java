package com.ablez.jookbiren.episode3.quiz.entity;

import com.ablez.jookbiren.episode3.user.entity.UserEp03;
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
public class WrongAnswerEp03 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wrongAnswerId;
    private String answer;
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEp03 user;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEp03 quiz;

    public WrongAnswerEp03(String answer, LocalDateTime time, UserEp03 user, QuizEp03 quiz) {
        this.answer = answer;
        this.time = time;
        this.user = user;
        this.quiz = quiz;
    }
}
