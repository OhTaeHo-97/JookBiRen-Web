package com.ablez.jookbiren.episode2.quiz.repository;

import com.ablez.jookbiren.episode2.quiz.entity.WrongAnswerEp02;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WrongAnswerRepository extends JpaRepository<WrongAnswerEp02, Long> {
}
