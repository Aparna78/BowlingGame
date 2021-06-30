import json
import requests
import os
import sys

def playBowling(gameId, laneNumber, numberOfPinKnocked):
    response = requests.put('http://localhost:8080/bowlingByLane/'+str(gameId)+'/'+str(laneNumber), json=numberOfPinKnocked)
    return response.text

def getTotalScoreByPlayerName(playerName):
    response = requests.get('http://localhost:8080/getTotalScoreByPlayerName/'+playerName)
    return response.text

def playCustom():
    no_of_pin_knocked = sys.argv[3]
    playBowling(gameId, laneNumber, no_of_pin_knocked)
    print "Game Update!!"

def sampleGame():
    playBowling(gameId, laneNumber, 10) #Nairobi's turn and she got strike in her first chance
    playBowling(gameId, laneNumber, 5)  #Denver's turn
    playBowling(gameId, laneNumber, 5)  #Denver's second chance and he got spare
    playBowling(gameId, laneNumber, 8)  #Tokyo's turn
    playBowling(gameId, laneNumber, 2)  #Tokyo's second chance and she got spare
    playBowling(gameId, laneNumber, 6)  #Nairobi's turn
    playBowling(gameId, laneNumber, 2)  #Nairobi's second turn
    playBowling(gameId, laneNumber, 10) #Denver got strike in his first chance but he will get one extra chance because it is last set
    playBowling(gameId, laneNumber, 10) #Denver get strike again 
    playBowling(gameId, laneNumber, 3)  #Tokyo's first chance
    playBowling(gameId, laneNumber, 4)  #Tokyo's second chanc
    

gameId = sys.argv[1]
laneNumber = sys.argv[2]
#playCustom()
sampleGame()
