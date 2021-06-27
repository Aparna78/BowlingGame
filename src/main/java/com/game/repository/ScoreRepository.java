package com.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.model.Score;

public interface ScoreRepository extends JpaRepository<Score, Long>{

}
