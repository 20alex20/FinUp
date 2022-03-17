import os
import shutil
from os.path import dirname

def main(number1, number2):
#     num1 = int(number1)
#     num2 = int(number2)
#
#     summ = num1 + num2
#     return "Сумма: " + str(summ)
    # cconn = sqlite3.connect("C:/Users/1/Desktop/p.db")
    # conn.cursor().execute('INSTALL INTO hello(name) VALUES("jj")')
    # filename = join(dirname(__file__), "filename.txt")
    # with open(dirname(__file__) + "/u.txt", 'r+') as f:
    #     s = f.read()
    # with open(os.environ["HOME"] + "/u.txt", 'r') as f:
    #     s += ' ' + f.read()
    # r = str(os.listdir(path=dirname(__file__)))
    shutil.copy(dirname(__file__) + "/g.txt", os.environ["HOME"] + "/")
    return 'l'
