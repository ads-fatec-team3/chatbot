from flask import request
from flask_restful import Resource

from .bot import Bot


class Response:
    def __init__(self, content: str, status: int) -> None:
        self.content = content
        self.status = status

    def response(self) -> tuple:
        options = {
            200: {'result': self.content},
            400: {'error': self.content},
            404: {'error': self.content},
        }
        return options[self.status], self.status


class MessageAPI(Resource):
    def post(self):
        try:
            message = request.json['message']
        except KeyError:
            r = Response('Inform the message', 400)
            return r.response()
        bot = Bot(message=message)
        r = Response(bot.response(), 200)
        return r.response()


class WelcomeAPI(Resource):
    def get(self):
        message = 'Oi! Eu sou a inteligência artificial Gruly. Estou aqui para tentar ajudá-lo. O que deseja?'
        r = Response(message, 200)
        return r.response()
