package com.elsprage.learning.persistance.repository;

import com.elsprage.learning.persistance.entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
    Set<GameResult> findByUserIdAndGameType(Long userId, String gameType);
}
