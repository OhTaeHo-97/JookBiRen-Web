package com.ablez.jookbiren.chatbot.user.repository;

import com.ablez.jookbiren.chatbot.user.entity.UserEp00;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotUserRepository extends JpaRepository<UserEp00, Long> {
}
