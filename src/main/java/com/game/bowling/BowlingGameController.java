package com.game.bowling;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.model.Player;
import com.game.model.Score;
import com.game.repository.PlayerRepository;
import com.game.repository.ScoreRepository;


@RestController
public class BowlingGameController {
	
	private final PlayerRepository playerRepository;
	private final ScoreRepository scoreRepository;

	BowlingGameController(PlayerRepository playerRepository, ScoreRepository scoreRepository) {
	    this.playerRepository = playerRepository;
	    this.scoreRepository = scoreRepository;
	  }
	
	//eg: curl -v localhost:8080/players
	  @GetMapping("/players")
	  List<Player> allPlayers() {
	    return playerRepository.findAll();
	  }
	  
	//eg: curl -v localhost:8080/scores
	  @GetMapping("/scores")
	  List<Score> allScore() {
	    return scoreRepository.findAll();
	  }
	  
}
