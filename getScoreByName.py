import json
import requests
import os
import sys

def getTotalScoreByPlayerName(playerName):
    response = requests.get('http://localhost:8080/getTotalScoreByPlayerName/'+playerName)
    return response.text

playerName = sys.argv[1]
print getTotalScoreByPlayerName(playerName)
