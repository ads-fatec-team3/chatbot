import nltk
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize
from nltk.stem import SnowballStemmer

from random import randint

from .data import RESPONSES


class Bot:
    def __init__(self, message: str) -> None:
        self.message = message
        self.result = ''
    
    def remove_stop_words(self) -> list:
        stop_words = set(stopwords.words('portuguese'))
        words = word_tokenize(self.message, language='portuguese')
        filtered_words = [word for word in words if word not in stop_words]
        return filtered_words
    
    def compare_responses(self) -> str:
        filtered_words = self.remove_stop_words()
        stem = SnowballStemmer('portuguese')

        for word in filtered_words:
            for response in RESPONSES:
                if stem.stem(word.lower()) in [stem.stem(x) for x in response['tags']]:
                    return response['responses'][randint(0, len(response['responses']) - 1)]
        return 'Não entendi... Poderia perguntar de alguma outra maneira?'
    
    def response(self) -> str:
        self.result = self.compare_responses()
        return self.result
