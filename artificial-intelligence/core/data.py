from datetime import datetime


def get_greeting():
    current_hour = datetime.now().hour
    if current_hour < 12:
        return 'Bom dia!'
    elif current_hour < 18:
        return 'Boa tarde!'
    else:
        return 'Boa noite!'



RESPONSES = [
    {
        'tags': ['oi', 'olá'],
        'responses': ['Olá, como posso ajudá-lo?', 'Oi!']
    },
    {
        'tags': ['trabalham', 'fazem'],
        'responses': ['A Anova ajuda pessoas com a auto escola']
    },
    {
        'tags': ['horário', 'funcionamento'],
        'responses': ['De segunda a sexta das 8h as 22h']
    },
    {
        'tags': ['quem', 'gruly'],
        'responses': ['Eu sou o Gruly', 'Meu nome é Gruly', 'Gruly é o nome que meus criadores me deram']
    },
    {
        'tags': ['agendamento', 'aula', 'marcar'],
        'responses': ['Fale com um atendente na aba conversas!']
    },
    {
        'tags': ['Obrigado', 'Valeu'],
        'responses': ['De nada! ^-^', 'Estou aqui para ajudá-lo!']
    },
    {
        'tags': ['remarcar', 'cancelamento', 'reagendar'],
        'responses': ['Fale com um atendente na aba conversas!']
    },
    {
        'tags': ['dia', 'tarde', 'noite'],
        'responses': [get_greeting()]
    }
]
