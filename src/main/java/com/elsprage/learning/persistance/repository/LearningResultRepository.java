package com.elsprage.learning.persistance.repository;

import com.elsprage.learning.persistance.entity.LearningResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LearningResultRepository extends JpaRepository<LearningResult, Long> {
    Set<LearningResult> findByUserId(Long userId);
}
