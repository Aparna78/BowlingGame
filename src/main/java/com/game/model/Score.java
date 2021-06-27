package com.game.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Score {
	@Id @GeneratedValue Long id;
	 Integer totalScore;
	 Integer currentScore;
	 Integer numberOfStrike;
	 Integer numberOfSpare;
	
}
