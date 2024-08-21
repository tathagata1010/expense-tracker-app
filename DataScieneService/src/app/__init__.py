from flask import Flask
from flask import request,jsonify
from service.messageService import MessageService

app = Flask(__name__)
app.config.from_pyfile('config.py')

messageService=MessageService()

@app.route('/v1/expense/message',methods=['POST'])
def message_handler():
    message = request.json.get("message")
    result=messageService.process_message(message)
    return jsonify(result)

@app.route('/v1/expense',methods=['GET'])
def get_handler():
    return 'Hello from Expense tracker AI service!'
    
if __name__ == '__main__':
    app.run(debug=True, host='localhost', port=8000)