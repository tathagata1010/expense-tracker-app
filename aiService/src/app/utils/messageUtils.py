import re

class MessageUtils:
    def isBankSms(self, message):
        # Define the list of words to check for
        words_to_check = ["withdrawal", "sent", "debit", "credit", "card", "credited","Avl Lmt", "balance", "statement", "A/C"]
        
        # Create a regular expression pattern that matches any of the words
        pattern = r'\b(?:' + '|'.join(words_to_check) + r')\b'
        
        # Search for the pattern in the message
        return bool(re.search(pattern, message, re.IGNORECASE))
    # def isCreditCardSms(self, message):
    #     # Define the list of words to check for
    #     words_to_check = ["card", "credited", "balance", "statement", "A/C"]

    #     # Create a regular expression pattern that matches any of the words
    #     pattern = r'\b(?:' + '|'.join(words_to_check) + r')\b'

    #     # Search for the pattern in the message
    #     return bool(re.search(pattern, message, re.IGNORECASE))