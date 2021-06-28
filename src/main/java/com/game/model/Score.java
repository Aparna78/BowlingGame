package com.game.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Score {
	@Id @GeneratedValue Long id;
	 int totalScore = 0;
	 int chanceOneScore = 0;
	 int chanceTwoScore = 0;
	 int numberOfStrike = 0;
	 int numberOfSpare = 0;
	
}
