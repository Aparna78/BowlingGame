import json
import requests
import os

def startGame():
    data = ['Nairobi','Denver','Tokyo','Berlin','Rio']
    response = requests.post('http://localhost:8080/startGame', json=data)
    return response.text

print startGame()
