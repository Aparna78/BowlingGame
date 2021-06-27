package com.game.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Game {
	
	@Id @GeneratedValue 
	Long id;
	@OneToMany(cascade = {CascadeType.ALL})
	List<Player> playerList;

}
