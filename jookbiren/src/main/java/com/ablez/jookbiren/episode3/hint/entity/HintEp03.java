package com.ablez.jookbiren.episode3.hint.entity;

import com.ablez.jookbiren.episode3.quiz.entity.QuizEp03;
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
public class HintEp03 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hintId;
    private String hint;
    private String hintImage;
    private Integer hintOrder;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEp03 quiz;
}
