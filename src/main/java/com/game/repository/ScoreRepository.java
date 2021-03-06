package com.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>{

}
