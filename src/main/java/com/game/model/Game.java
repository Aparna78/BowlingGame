package com.game.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Game {
	
	@Id @GeneratedValue 
	Long id;
	@OneToMany
	List<Player> playerList;

}
