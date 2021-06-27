package com.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{
	 Player findByName(String name);
}
