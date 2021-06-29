package com.game.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>{
	Optional<Game> findGameById(long gameId);

}
