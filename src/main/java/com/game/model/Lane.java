package com.game.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Lane {
	@Id @GeneratedValue 
	Long id;
	int laneNumber;
	int activeSetNumber = 1;
	@OneToOne(cascade = {CascadeType.ALL})
	Player activePlayer;
	@OneToMany(cascade = {CascadeType.ALL})
	List<Player> playerList;

}
