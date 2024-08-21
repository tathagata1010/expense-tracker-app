from utils.messageUtils import MessageUtils
from service.llmService import LLMService

class MessageService:
    def __init__(self):
        self.messageUtils = MessageUtils()
        self.llmService = LLMService()
    
    def process_message(self, message):
        if self.messageUtils.isBankSms(message):
            return self.llmService.runLLM(message)
        else:
            return None