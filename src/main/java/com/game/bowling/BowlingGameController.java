package com.game.bowling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.model.Game;
import com.game.model.Player;
import com.game.model.Score;
import com.game.repository.GameRepository;
import com.game.repository.PlayerRepository;
import com.game.repository.ScoreRepository;



@RestController
public class BowlingGameController {
	
	private final PlayerRepository playerRepository;
	private final ScoreRepository scoreRepository;
	private final GameRepository gameRepository;

	BowlingGameController(PlayerRepository playerRepository, ScoreRepository scoreRepository, GameRepository gameRepository) {
	    this.playerRepository = playerRepository;
	    this.scoreRepository = scoreRepository;
	    this.gameRepository = gameRepository;
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
	  
	//eg: curl -X POST localhost:8080/startGame -H 'Content-type:application/json' -d '["a","b","c"]'
	  @PostMapping("/startGame")
	  String startGame(@RequestBody List<String> playerNameList) {
	    int noOfPlayer = playerNameList.size();
	    Game g = new Game();
	    List<Player> playerList = new ArrayList<Player>();
	    for (int i=0; i<noOfPlayer; i++) {
	    	Player p = new Player();
	    	p.setName(playerNameList.get(i));
	    	p.setLaneNumber(i%3);
	    	playerList.add(p);
	    }
	    g.setPlayerList(playerList);
	    gameRepository.save(g);
	    return "Game with Id" + g.getId() + "is On !!";
	  }
	  
}
