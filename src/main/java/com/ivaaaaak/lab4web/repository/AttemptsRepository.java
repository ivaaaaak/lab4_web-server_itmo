package com.ivaaaaak.lab4web.repository;

import com.ivaaaaak.lab4web.model.attempts.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptsRepository extends JpaRepository<Attempt, Long> {
    void deleteAllByUserId(Long userID);
    List<Attempt> findAllByUserId(Long userID);
}
