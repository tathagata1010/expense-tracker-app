from typing import Optional
from dotenv import load_dotenv,dotenv_values
from langchain_core.prompts import ChatPromptTemplate,MessagesPlaceholder
from langchain_core.pydantic_v1 import BaseModel, Field
from langchain_openai import ChatOpenAI
from langchain_mistralai import ChatMistralAI
from datetime import datetime

from langchain_core.utils.function_calling import convert_to_openai_tool
import os
from schema.Expense import Expense

class LLMService:
    def __init__(self):
        load_dotenv()
        self.prompt=ChatPromptTemplate.from_messages(
            [
                (
                    "system",
                    "You are an advanced information extraction algorithm. Your task is to extract only the relevant data explicitly mentioned in the text."
                    "If you do not know the value of an attribute asked to extract, return null for the attribute's value."
                    "Do not infer or assume values; only extract explicit information present in the text."
                    "Ensure accuracy and relevance in the extraction process, focusing solely on the requested attributes."
                ),
                ("human","{text}")
            ]
        )
        self.apiKey=os.getenv("MISTRALAI_API_KEY")
        self.llm=ChatMistralAI(api_key=self.apiKey,model_name="mistral-large-latest",temperature=0)
        # self.llm=ChatOpenAI(api_key=self.apiKey,model_name="gpt-3.5-turbo-0125",temperature=0.0)
        self.runnable= self.prompt | self.llm.with_structured_output(schema=Expense)
        
    def runLLM(self,message):
        extracted_expense=self.runnable.invoke({"text":message})
        # If the time is missing, set it to the current time
        if extracted_expense.time is None:
            extracted_expense.time = datetime.now().strftime("%H:%M:%S")
        return extracted_expense