package com.game.service;

import java.util.ArrayList;
import java.util.List;

import com.game.model.Game;
import com.game.model.Lane;
import com.game.model.Player;
import com.game.model.Score;

public class BowlingGameService {

	public static final int NUMBER_OF_PLAYER_PER_LANE = 3;
	public static final int MAX_CHANCE = 2;
	public static final int STRIKE_BONUS = 10;
	public static final int SPARE_BONUS = 5;
	public static final int MAX_NUMBER_OF_SET = 2;
	public static final int MAX_NUMBER_OF_PIN = 10;
	int countBowlRoll = 0;

	public List<Lane> prepareLane(List<String> playerNameList) {
		List<Lane> lanelist = new ArrayList<Lane>();
		int noOfPlayer = playerNameList.size();
		List<Player> playerList = new ArrayList<Player>();
		int playerNumber = 0;
		int laneNumber = 1;
		for (int i=0; i<noOfPlayer; i++) {
			if (playerList.size() == NUMBER_OF_PLAYER_PER_LANE) {
				Lane l = new Lane();
				l.setCurrentPlayer(playerList.get(0));
				l.setNextPlayer(playerList.get(1));
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
			playerNumber = playerNumber % NUMBER_OF_PLAYER_PER_LANE;
			p.setPlayerNumber(playerNumber+1);
			playerNumber += 1;
			playerList.add(p);

		}
		if (playerList.size() != 0) {
			Lane l = new Lane();
			l.setCurrentPlayer(playerList.get(0));
			l.setNextPlayer(playerList.get(1));
			l.setPlayerList(playerList);
			l.setLaneNumber(laneNumber);
			lanelist.add(l);
		}
		return lanelist;
	}

	public Score getScoreOfActivePlayer(Game game, Lane lane, int numberOfPinKnocked) {
		Player currentPlayer = lane.getCurrentPlayer();
		//update current player's chance
		currentPlayer.setChancePlayed(currentPlayer.getChancePlayed()+1);
		
		//update current player's score 
		if (numberOfPinKnocked == 10) {
			currentPlayer.getScore().setTotalScore(currentPlayer.getScore().getTotalScore()+numberOfPinKnocked+STRIKE_BONUS);
			currentPlayer.getScore().setNumberOfStrike(currentPlayer.getScore().getNumberOfStrike()+1);	
		}
		else {
			if (currentPlayer.getChancePlayed() == 1) {
				currentPlayer.getScore().setChanceOneScore(numberOfPinKnocked);
				currentPlayer.getScore().setTotalScore(currentPlayer.getScore().getTotalScore()+numberOfPinKnocked);
			}
			else {
				currentPlayer.getScore().setChanceTwoScore(numberOfPinKnocked);
				int chanceOneScore = currentPlayer.getScore().getChanceOneScore();
				if (chanceOneScore+numberOfPinKnocked == MAX_NUMBER_OF_PIN) {
					currentPlayer.getScore().setTotalScore(currentPlayer.getScore().getTotalScore()+numberOfPinKnocked+SPARE_BONUS);
					currentPlayer.getScore().setNumberOfSpare(currentPlayer.getScore().getNumberOfSpare()+1);
				}
				else {
					currentPlayer.getScore().setTotalScore(currentPlayer.getScore().getTotalScore()+numberOfPinKnocked);
				}

			}
		}
		
		int activeSetNumber = lane.getActiveSetNumber();
		//update set number
		if ((currentPlayer.getPlayerNumber() == lane.getPlayerList().size() & activeSetNumber != MAX_NUMBER_OF_SET 
				& (currentPlayer.getChancePlayed() == 2 || numberOfPinKnocked == MAX_NUMBER_OF_PIN))
				|| (activeSetNumber == MAX_NUMBER_OF_SET & currentPlayer.getPlayerNumber() == lane.getPlayerList().size() 
				& (currentPlayer.getChancePlayed() == 3 || (currentPlayer.getChancePlayed() == 2 & numberOfPinKnocked < MAX_NUMBER_OF_PIN))))
			lane.setActiveSetNumber(lane.getActiveSetNumber()+1);
		
		//update current and next player
		if ((activeSetNumber < MAX_NUMBER_OF_SET & currentPlayer.getChancePlayed() == 2) 
			|| (activeSetNumber < MAX_NUMBER_OF_SET  & numberOfPinKnocked == MAX_NUMBER_OF_PIN) 
			|| (activeSetNumber == MAX_NUMBER_OF_SET & currentPlayer.getChancePlayed() == MAX_CHANCE & numberOfPinKnocked < MAX_NUMBER_OF_PIN)
			|| (activeSetNumber == MAX_NUMBER_OF_SET & currentPlayer.getChancePlayed() == 3)
			|| (activeSetNumber == MAX_NUMBER_OF_SET & currentPlayer.getChancePlayed() == MAX_CHANCE & numberOfPinKnocked == MAX_NUMBER_OF_PIN)) {
			currentPlayer.setChancePlayed(0);
			currentPlayer.getScore().setChanceOneScore(0);
			currentPlayer.getScore().setChanceTwoScore(0);
			lane.setCurrentPlayer(lane.getNextPlayer());
			int nextPlayerNumber = lane.getNextPlayer().getPlayerNumber();
			int nextnextPlayerNumber = nextPlayerNumber % NUMBER_OF_PLAYER_PER_LANE;
			List<Player> playerInCurrentLane = lane.getPlayerList();
			for (int i = 0; i < playerInCurrentLane.size(); i++) {
				  if (playerInCurrentLane.get(i).getPlayerNumber() == nextnextPlayerNumber+1)
					  lane.setNextPlayer(playerInCurrentLane.get(i));					  
			  }			
		}
			
		return currentPlayer.getScore();
	}

	public boolean isValidData(Lane l, int numberOfPinKnocked) {
		if ((l.getCurrentPlayer().getChancePlayed() == 0 & numberOfPinKnocked > 10) 
			|| (l.getActiveSetNumber() != MAX_NUMBER_OF_SET & l.getCurrentPlayer().getChancePlayed() == 1 & numberOfPinKnocked > MAX_NUMBER_OF_PIN - l.getCurrentPlayer().getScore().getChanceOneScore())
			|| (l.getActiveSetNumber() == MAX_NUMBER_OF_SET & l.getCurrentPlayer().getChancePlayed() == 2 & numberOfPinKnocked > 10)
			|| (l.getActiveSetNumber() == MAX_NUMBER_OF_SET & l.getCurrentPlayer().getChancePlayed() == 1 & l.getCurrentPlayer().getScore().getChanceOneScore()!= MAX_NUMBER_OF_PIN & numberOfPinKnocked > MAX_NUMBER_OF_PIN-l.getCurrentPlayer().getScore().getChanceOneScore()))
			return false;
		return true;
	}
}
