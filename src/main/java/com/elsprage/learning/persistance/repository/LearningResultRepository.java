package com.elsprage.learning.persistance.repository;

import com.elsprage.learning.persistance.entity.LearningResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface LearningResultRepository extends JpaRepository<LearningResult, Long> {
    Set<LearningResult> findByUserId(Long userId);

    @Query("SELECT lr FROM LearningResult lr WHERE lr.userId = :userId and lr.packetId = :packetId and lr.learningMode = :learningMode ORDER BY lr.date DESC LIMIT 1")
    Optional<LearningResult> getLastLearningResult(@Param("userId") Long userId,
                                                   @Param("packetId") Long packetId,
                                                   @Param("learningMode") String learningMode);
}
