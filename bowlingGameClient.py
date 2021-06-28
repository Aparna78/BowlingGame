import json
import requests
import os

def startGameRequest():
    data = ['a','b','c','d','e']
    response = requests.post('http://localhost:8080/startGame', json=data)
    return response.text

print startGameRequest()
