package com.sedlacek.quiz.repositories;

import com.sedlacek.quiz.models.LoginSession;
import com.sedlacek.quiz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSessionRepository extends JpaRepository<LoginSession, Long> {
}
