# BowlingGame

Diagrams:
  https://docs.google.com/presentation/d/1xs71MB3rfU99YhiDRJRku_IOyOWYqakULvG_fD698UE/edit#slide=id.gb8002e81e5_0_24

constants:
  NUMBER_OF_PLAYER_PER_LANE = 3;
	MAX_CHANCE = 2;
	STRIKE_BONUS = 10;
	SPARE_BONUS = 5;
	MAX_NUMBER_OF_SET = 2;
	MAX_NUMBER_OF_PIN = 10;
  NUMBER_OF_LANE_AVAILABLE = 3;
	MIN_NUMBER_OF_PLAYER = 2;


API request commands:
  * to start the game:
    curl -X POST localhost:8080/startGame -H 'Content-type:application/json' -d 'List of player names' | jq
    eg: curl -X POST localhost:8080/startGame -H 'Content-type:application/json' -d '["nairobi","denver","tokyo"]' | jq

  * to roll the bowl:
    curl -X PUT localhost:8080/bowlingByLane/{gameId}/{laneNumber} -H 'Content-type:application/json' -d 'no of pin knocked'
    eg : curl -X PUT localhost:8080/bowlingByLane/1/1 -H 'Content-type:application/json' -d '1'

  * to delete game:
    curl -X DELETE localhost:8080/deleteGameById/{gameId}
    eg : curl -X DELETE localhost:8080/deleteGameById/1


run instructions:
    * to start the game run 
        python startGame.py <no of player> <player name 1> .... <player name n> | jq
    * to play the game run
         python playBowling.py <game Id> <lane number> <no of pin knocked> | jq
  
  
