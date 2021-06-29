package com.game.bowling;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Game;
import com.game.model.Lane;
import com.game.model.Player;
import com.game.model.RecordNotFoundException;
import com.game.model.Score;
import com.game.repository.GameRepository;
import com.game.repository.PlayerRepository;
import com.game.repository.ScoreRepository;
import com.game.service.BowlingGameService;

@RestController
public class BowlingGameController {

	private final PlayerRepository playerRepository;
	private final ScoreRepository scoreRepository;
	private final GameRepository gameRepository;
	public static final int NUMBER_OF_LANE_AVAILABLE = 3;
	public static final int MIN_NUMBER_OF_PLAYER = 2;

	int chance;
	int strikeBonus = 10;
	int spareBonus = 5;
	int numberOfLaneAvailable = 3;
	int numberOfPlayerAllowedInEachLane = 3;
	int maxChance = 2;

	private BowlingGameService bowlingGameService = new BowlingGameService();


	BowlingGameController(PlayerRepository playerRepository, ScoreRepository scoreRepository, GameRepository gameRepository) {
		this.playerRepository = playerRepository;
		this.scoreRepository = scoreRepository;
		this.gameRepository = gameRepository;
	}

	@GetMapping("/players")
	List<Player> allPlayers() {
		return playerRepository.findAll();
	}

	@GetMapping("/scores")
	List<Score> allScore() {
		return scoreRepository.findAll();
	}

	@PostMapping("/startGame")
	String startGame(@RequestBody List<String> playerNameList) throws JsonProcessingException {			
		if (playerNameList.size() < MIN_NUMBER_OF_PLAYER)
			return "Atleast two player is needed to start the game";
		if (playerNameList.size() > NUMBER_OF_LANE_AVAILABLE * BowlingGameService.NUMBER_OF_PLAYER_PER_LANE)
			return "Atmost number of player allowed to start the game is "+numberOfLaneAvailable * numberOfPlayerAllowedInEachLane;
		
		Game g = new Game();
		g.setLaneList(bowlingGameService.prepareLane(playerNameList));
		gameRepository.save(g);
		
		return new ObjectMapper().writeValueAsString(g);
	}
	
	@PutMapping("/bowlingByLane/{gameId}/{laneNumber}")
	String playBowling(@PathVariable long gameId, @PathVariable int laneNumber, @RequestBody int numberOfPinKnocked){ 
		Game g = gameRepository.findGameById(gameId).orElseThrow(() -> new RecordNotFoundException("Game id '" + gameId + "' does no exist"));
		
		Lane l = null;
		List<Lane> lanelist = g.getLaneList();
		for (int i =0; i<lanelist.size(); i++) {
			if (lanelist.get(i).getLaneNumber() == laneNumber) {
				l = lanelist.get(i);
				break;
			}
		}
		if (l == null) {
			throw new RecordNotFoundException("Lane Number '" + laneNumber + "' does no exist");
		}
		if (l.getActiveSetNumber() > BowlingGameService.MAX_NUMBER_OF_SET) {
			return "Game Over!!";
		}
		
		if (bowlingGameService.isValidData(l, numberOfPinKnocked)) {
					Score s = bowlingGameService.getScoreOfActivePlayer(g, l, numberOfPinKnocked);
					scoreRepository.save(s);
		}else
			return "Number of pin knocked is not valid";
		
		return "Game Updated !!";
	}	  

	@GetMapping("/getActivePlayerNameByLaneNumber/{gameId}/{laneNumber}")
	String getActivePlayerName(@PathVariable long gameId, @PathVariable int laneNumber) {
		Game g = gameRepository.findGameById(gameId).orElseThrow(() -> new RecordNotFoundException("Game id '" + gameId + "' does no exist"));;
		
		List<Lane> lanelist = g.getLaneList();
		for (int i =0; i<lanelist.size(); i++) {
			if (lanelist.get(i).getLaneNumber() == laneNumber)
				return lanelist.get(i).getCurrentPlayer().getName();
		}
		
		return "Name not found";
	}
	
	@GetMapping("/getTotalScoreByPlayerName/{playerName}")
	String getTotalScoreByPlayerName(@PathVariable String playerName) throws JsonProcessingException{
		return new ObjectMapper().writeValueAsString(playerRepository.findByName(playerName).getScore());
	}

	
	@DeleteMapping("/deleteGameById/{id}")
	String deleteGame(@PathVariable Long id) {
		gameRepository.deleteById(id);
		return "Game Over!!";
	}
}
