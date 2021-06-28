package com.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.model.Lane;

@Repository
public interface LaneRepository extends JpaRepository<Lane, Long>{
	Lane findBylaneNumber(int laneNumber);

}
