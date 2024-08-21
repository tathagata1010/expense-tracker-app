from langchain_core.pydantic_v1 import BaseModel, Field
from typing import Optional

class Expense(BaseModel):
    amount: Optional[str] = Field(title="expense", description="Expense made in transaction")
    transaction_type: Optional[str] = Field(title="Transaction Type", description="Type of transaction, either 'spent' or 'received'")
    merchant: Optional[str] = Field(title="merchant", description="Merchant name whom the transaction has been made")
    currency: Optional[str] = Field(title="currency", description="currency of transaction")
    date: Optional[str] = Field(title="date", description="date of transaction")
    time: Optional[str] = Field(title="Time", description="Time of the transaction (HH:MM:SS)")