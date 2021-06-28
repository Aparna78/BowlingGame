# BowlingGame

api request commands:
* to start the game:
curl -X POST localhost:8080/startGame -H 'Content-type:application/json' -d '["a","b","c"]'

* to roll the bowl:
curl -X PUT localhost:8080/bowlingByLane/1 -H 'Content-type:application/json' -d '1'

run instructions:
run python bowlingGameClient.py | jq
