import json
import requests
import os
import sys

def startGameCustom():
    data = ['Nairobi','Denver','Tokyo']
    response = requests.post('http://localhost:8080/startGame', json=data)
    return response.text

def startGame():
    n = int(sys.argv[1])
    data = []
    for i in range(1, n+1):
        data.append(sys.argv[i+1])
    response = requests.post('http://localhost:8080/startGame', json=data)
    return response.text

print startGame()
