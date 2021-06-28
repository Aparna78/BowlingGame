package com.game.bowling;

import java.util.ArrayList;
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
import com.game.model.Score;
import com.game.repository.GameRepository;
import com.game.repository.LaneRepository;
import com.game.repository.PlayerRepository;
import com.game.repository.ScoreRepository;

@RestController
public class BowlingGameController {
	
	private final PlayerRepository playerRepository;
	private final ScoreRepository scoreRepository;
	private final GameRepository gameRepository;
	private final LaneRepository laneRepository;
	
	int chance;
	int strikeBonus = 10;
	int spareBonus = 5;
	int numberOfLaneAvailable = 3;
	int numberOfPlayerAllowedInEachLane = 3;
	int maxChance = 2;
	


	BowlingGameController(PlayerRepository playerRepository, ScoreRepository scoreRepository, GameRepository gameRepository, LaneRepository laneRepository) {
	    this.playerRepository = playerRepository;
	    this.scoreRepository = scoreRepository;
	    this.gameRepository = gameRepository;
	    this.laneRepository = laneRepository;
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
	  String startGame(@RequestBody List<String> playerNameList) throws JsonProcessingException {
		  Game g = new Game();	
			List<Lane> lanelist = new ArrayList<Lane>();
	    int noOfPlayer = playerNameList.size();
	    List<Player> playerList = new ArrayList<Player>();
	    int playerNumber = 0;
	    int laneNumber = 1;
	    
	    if (playerNameList.size() > numberOfLaneAvailable * numberOfPlayerAllowedInEachLane)
	    	return "Atmost number of player allowed to start the game is "+numberOfLaneAvailable * numberOfPlayerAllowedInEachLane;
	    
	    for (int i=0; i<noOfPlayer; i++) {
	    	if (playerList.size() == numberOfPlayerAllowedInEachLane) {
	    		Lane l = new Lane();
	    		l.setActivePlayer(playerList.get(0));
	    		l.setPlayerList(playerList);
	    		l.setLaneNumber(laneNumber);
	    		laneNumber += 1;
	    		lanelist.add(l);
	    		playerNumber = 0;
	    		playerList = new ArrayList<Player>();
	    	}
	    		Player p = new Player();
	    		p.setScore(new Score());
	    		p.setName(playerNameList.get(i));
	    		playerNumber = playerNumber % numberOfPlayerAllowedInEachLane;
	    		p.setPlayerNumber(playerNumber+1);
	    		playerNumber += 1;
	    		playerList.add(p);
	    	
	    }
	    if (playerList.size() != 0) {
	    	Lane l = new Lane();
	    	l.setActivePlayer(playerList.get(0));
    		l.setPlayerList(playerList);
    		l.setLaneNumber(laneNumber);
    		lanelist.add(l);
	    }
	    
	    g.setLaneList(lanelist);
	    gameRepository.save(g);
	    return new ObjectMapper().writeValueAsString(g);
	  }
	  
	  @GetMapping("/getActivePlayerNameByLaneNumber/{laneNumber}")
	  String getActivePlayerName(@PathVariable int laneNumber) {
		  Lane l = laneRepository.findBylaneNumber(laneNumber);
		  return l.getActivePlayer().getName();
	  }
	  
	//eg: curl -X PUT localhost:8080/bowlingByLane/1 -H 'Content-type:application/json' -d '1'
	  @PutMapping("/bowlingByLane/{laneNumber}")
	  String playBowling(@PathVariable int laneNumber, @RequestBody int numberOfPinKnocked) { 
		  
		  Lane l = laneRepository.findBylaneNumber(laneNumber);		  
		  Player activePlayer = l.getActivePlayer();
		  List<Player> playerInCurrentLane = l.getPlayerList();
		  
		  if (activePlayer.getChance() == 2 || numberOfPinKnocked == 10) {
			  int activePlayerNumber = activePlayer.getPlayerNumber();
			  int nextPlayerNumber = activePlayerNumber % numberOfPlayerAllowedInEachLane;
			  for (int i = 0; i < playerInCurrentLane.size(); i++) {
				  if (playerInCurrentLane.get(i).getPlayerNumber() == nextPlayerNumber+1)
					  l.setActivePlayer(playerInCurrentLane.get(i));					  
			  }
		  }
		  
		  int activeSetNumber = l.getActiveSetNumber();
		  l.setActiveSetNumber(activeSetNumber+1);
		  
		  if (activeSetNumber > 10)
			  return "Game Over for Lane "+l.getLaneNumber();
		  
		  int activePlayerChance = activePlayer.getChance();
		  int nextChanceNumberForActivePlayer = activePlayerChance % maxChance;
		  activePlayer.setChance(nextChanceNumberForActivePlayer+1);

		  
		  if (numberOfPinKnocked == 10) {
			  activePlayer.getScore().setTotalScore(activePlayer.getScore().getTotalScore()+numberOfPinKnocked+strikeBonus);
			  activePlayer.getScore().setNumberOfStrike(activePlayer.getScore().getNumberOfStrike()+1);	
			  }
		  else {
			  	if (activePlayerChance == 1) {
			  		activePlayer.getScore().setChanceOneScore(numberOfPinKnocked);
			  		activePlayer.getScore().setTotalScore(activePlayer.getScore().getTotalScore()+numberOfPinKnocked);
			  		}
			  	else {
			  		activePlayer.getScore().setChanceTwoScore(numberOfPinKnocked);
			  			int chanceOneScore = activePlayer.getScore().getChanceOneScore();
			  			if (chanceOneScore+numberOfPinKnocked == 10) {
			  				activePlayer.getScore().setTotalScore(activePlayer.getScore().getTotalScore()+numberOfPinKnocked+spareBonus);
			  				activePlayer.getScore().setNumberOfSpare(activePlayer.getScore().getNumberOfSpare()+1);
			  			}
			  			else {
			  				activePlayer.getScore().setTotalScore(activePlayer.getScore().getTotalScore()+numberOfPinKnocked);
			  			}
			  			
			  		}
			  }
		  
		  if (activePlayerChance == 2 || numberOfPinKnocked == 10) {
			  activePlayer.getScore().setChanceOneScore(0);
	  		  activePlayer.getScore().setChanceTwoScore(0);
		  }
			  
		return "Game updated: " +activePlayer;
	  }	  
	  
	  //curl -X DELETE localhost:8080/deleteGameById/1
	  @DeleteMapping("/deleteGameById/{id}")
	  String deleteGame(@PathVariable Long id) {
	    gameRepository.deleteById(id);
	    return "Game Over!!";
	  }
}
