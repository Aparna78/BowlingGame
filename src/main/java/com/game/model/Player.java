package com.game.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Player {

   @Id @GeneratedValue Long id;
   String name;
   int playerNumber;
   int chance = 1;
   @OneToOne(cascade = {CascadeType.ALL})
   Score score;
  
}