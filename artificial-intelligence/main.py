from flask import Flask, request
from flask_restful import Api
from flask_cors import CORS

from core.api import MessageAPI, WelcomeAPI


app = Flask(__name__)
cors = CORS(app, resources={r"*": {"origins": "*"}})
api = Api(app)

api.add_resource(WelcomeAPI, '/')
api.add_resource(MessageAPI, '/send-message')

if __name__ == '__main__':
    app.run(debug=True)
