package com.ablez.jookbiren.episode1.quiz.repository;

import com.ablez.jookbiren.episode1.quiz.entity.WrongAnswerEp01;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WrongAnswerRepository extends JpaRepository<WrongAnswerEp01, Long> {
}
