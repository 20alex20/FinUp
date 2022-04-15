import sqlite3
from datetime import datetime as dt
from werkzeug.security import generate_password_hash, check_password_hash
from os import environ
import shutil
from os.path import dirname, exists


def start():
    if not exists(environ["HOME"] + "/db.sqlite3"):
        shutil.copy(dirname(__file__) + "/db.sqlite3", environ["HOME"] + "/")
    return


session_file = environ["HOME"] + "/id_user.txt"
def format(string, *args):
    for i in args:
        ind1 = string.index('{')
        ind2 = string.index('}')
        string = string[:ind1] + str(i) + string[ind2 + 1:]
    return string

def get_id_user():
    with open(session_file, "r") as f:
        return int(f.read().split('.')[0])

def get_full_name():
    with open(session_file, "r") as f:
        return f.read().split('.')[2]

def get_username_email():
    with open(session_file, "r") as f:
        return f.read().split('.')[1]

def write_all(first, second, third):
    first = str(first)
    with open(session_file, "w+") as f:
        f.write(first + '.'+ second + '.' + third)


register_query = 'INSERT INTO users(username_email, password_hash, full_name) ' \
                      'VALUES("{username_email}", "{password_hash}", "{full_name}")'
login_query = 'SELECT id_user, password_hash, full_name FROM ' \
                   'users WHERE username_email="{username_email}"'
is_there_username_email = 'SELECT username_email FROM users ' \
                               'WHERE username_email="{username_email}"'
edit_about_me_query = "UPDATE users SET username_email='{username_email}', full_name='{full_name}' WHERE id_user={id_user}"
delete_my_account_query = 'DELETE FROM users WHERE id_user={id_user}'

add_category_query = 'INSERT INTO categories(name, id_user, description) ' \
                          'VALUES("{name}", {id_user}, "{description}")'
get_categories_query = 'SELECT id_category, name, description FROM ' \
                            'categories WHERE id_user={id_user}'  #
is_there_category = 'SELECT id_category FROM categories WHERE name="{name}", id_user={id_user}'
edit_category_query = "UPDATE categories SET name='{name}', description='{description}' WHERE id_category={id_category}"
delete_category_query = 'DELETE FROM categories WHERE id_category={id_category}'

add_deposit_category_query = 'INSERT INTO deposit_categories(name, id_user, description) ' \
                                  'VALUES("{name}", {id_user}, "{description}")'
get_deposit_categories_query = 'SELECT id_deposit_category, name, description FROM ' \
                                    'deposit_categories WHERE id_user={id_user}'  #
is_there_deposit_category = 'SELECT id_deposit_category FROM deposit_categories WHERE name="{name}", ' \
                                 'id_user={id_user}'
edit_deposit_category_query = "UPDATE deposit_categories SET name='{name}', description='{description}' WHERE id_deposit_category={id_deposit_category}"
delete_deposit_category_query = 'DELETE FROM deposit_categories WHERE ' \
                                     'id_deposit_category={id_deposit_category}'

add_purchase_query = 'INSERT INTO purchases(date_time_add, id_category, id_bank_account, sum, date, ' \
                          'comment) VALUES("{date_time_add}", {id_category}, {id_bank_account}, {sum}, ' \
                          '"{date}", "{comment}")'
get_purchases_query = 'SELECT purchases.id_purchase, purchases.id_category, purchases.id_bank_account, ' \
                           'purchases.sum, purchases.date, purchases.comment FROM purchases ' \
                           'INNER JOIN categories ON purchases.id_category = categories.id_category ' \
                           'AND categories.id_user={id_user}'
get_sum_purchase_query = 'SELECT sum FROM purchases WHERE purchase={purchase}'
get_purchases_query2 = 'SELECT id_purchase, sum, date FROM ' \
                            'purchases WHERE id_category={id_category}'  #
edit_purchase_query = "UPDATE purchases SET id_category={id_category}, id_bank_account={id_bank_account}, sum={sum}, date='{date}', comment='{comment}' WHERE id_purchase={id_purchase}"
delete_purchase_query = 'DELETE FROM purchases WHERE id_purchase={id_purchase}'

add_bank_account_query = 'INSERT INTO bank_accounts(name, id_user, current_sum, description) ' \
                              'VALUES("{name}", {id_user}, {current_sum}, "{description}")'
get_bank_accounts_query = 'SELECT id_bank_account, name, current_sum, description FROM ' \
                               'bank_accounts WHERE id_user={id_user}'  #
is_there_bank_account = 'SELECT id_bank_account FROM bank_accounts WHERE name="{name}", id_user={id_user}'
get_current_sum_query = 'SELECT current_sum FROM bank_accounts WHERE id_bank_account={id_bank_account}'
edit_sum_query = 'UPDATE bank_accounts SET current_sum={current_sum} WHERE id_bank_account={id_bank_account}'
edit_bank_account_query = "UPDATE bank_accounts SET name='{name}', description='{description}' WHERE id_bank_account={id_bank_account}"
delete_bank_account_query = 'DELETE FROM bank_accounts WHERE id_bank_account={id_bank_account}'

add_deposit_query = 'INSERT INTO deposits(date_time_add, id_deposit_category, id_bank_account, sum,' \
                         'date, comment) VALUES("{date_time_add}", {id_deposit_category}, {id_bank_account}, ' \
                         '{sum}, "{date}", "{comment}")'
get_deposits_query = 'SELECT deposits.id_deposit, deposits.id_deposit_category, deposits.id_bank_account,' \
                          ' deposits.sum, deposits.date, deposits.comment FROM deposits ' \
                          'INNER JOIN deposit_categories ON deposits.id_deposit_category = ' \
                          'deposit_categories.id_deposit_category AND deposit_categories.id_user={id_user}'
get_sum_deposit_query = 'SELECT sum FROM deposits WHERE deposit={deposit}'
get_deposits_query2 = 'SELECT id_deposit, sum, date FROM ' \
                           'deposits WHERE id_deposit_category={id_deposit_category}'  #
edit_deposit_query = "UPDATE deposits SET id_deposit_category={id_deposit_category}, id_bank_account={id_bank_account} sum={sum}, date='{date}', comment='{comment}' WHERE id_deposit={id_deposit}"
delete_deposit_query = 'DELETE FROM deposits WHERE id_deposit={id_deposit}'

bd_name = environ["HOME"] + "/db.sqlite3"


def get_data(query):  # для получения данных
    try:
        open(bd_name, 'rb').close()
    except FileNotFoundError:
        raise Exception("Файл не найден")
    connection = sqlite3.connect(bd_name)
    try:
        data = connection.cursor().execute(query).fetchall()
    except Exception as e:
        connection.close()
        return []
    connection.close()
    return data

def do_query(query):  # для добавления/изменения данных
    try:
        open(bd_name, 'rb').close()
    except FileNotFoundError:
        raise Exception("Файл не найден")
    connection = sqlite3.connect(bd_name)
    connection.cursor().execute(query)
    try:
        pass
    except Exception:
        connection.close()
        raise Exception("В доступе отказано")
    connection.commit()
    connection.close()
    return "ОК"

def to_line_list(arr, cut=None):
    ans = []
    for i in arr:
        if cut is not None:
            i = i[:cut]
        ans.extend(map(str, i))
    return ans


def get_all_data():
    ans = []
    id_user = get_id_user()
    for i in sorted(["get_categories_query", "get_deposit_categories_query", "get_purchases_query2", "get_bank_accounts_query", "get_deposits_query2"]):
        ans.append(format(i, id_user))
    return ans


def login(username_email, password):
    ans = get_data(format(login_query, username_email))
    if ans and check_password_hash(ans[0][1], password):
        id_user = ans[0][0]
        full_name = ans[0][2]
        write_all(id_user, full_name, username_email)
        return "Вход разрешен"
    return "Неверный логин или пароль"


default = ["Автомобиль", "Отдых и развлечения", "Продукты", "Кафе и растораны", "Одежда", "Здоровье и фитнес", "Подарки", "Поездки"]
default1 = ["Зарплата", "Аренда", "Родственники"]
def register(username_email, password, full_name):
    # shutil.copy(dirname(__file__) + "/db.sqlite3", environ["HOME"] + "/")
    # return 'ok'
    ans = get_data(format(is_there_username_email, username_email))
    if ans:
        return "Аккаунт на эту почту уже зарегистрирован"
    do_query(format(register_query, username_email, generate_password_hash(password), full_name))
    message = login(username_email, password)
    if message == "Вход разрешен":
        for i in default:
            add_category(i, "")
        for j in default1:
            add_deposit_category(j, "")
        add_bank_account("Наличные", 0, "")
    return message


def edit_about_me(username_email, full_name):
    if username_email != get_username_email():
        ans = get_data(format(is_there_username_email, username_email))
        if ans:
            return "Аккаунт на эту почту уже зарегистрирован"
    do_query(format(edit_about_me_query, username_email, full_name, get_id_user()))
    return "Данные изменены"

def logout():
    with open(session_file, 'w') as f:
        f.write("")
    return "До свидания"

def delete_my_account():
    do_query(format(delete_my_account_query, get_id_user()))
    logout()
    return "Аккаунт удалён успешно"


def add_category(name, description=""):
    if search_category(name, get_categories()):
       return "Категория с таким названием уже существует"
    do_query(format(add_category_query, name, get_id_user(), description))
    return "Данные изменены"

def search_category(name, llist):
    for category in llist:
        if category[1] == name:
            return category[0]
    return False

def get_categories():
    return get_data(format(get_categories_query, get_id_user()))

def edit_category(id_category, name, description):
    ans = search_category(name, get_categories())
    if ans and ans != id_category:
        return "Категория с таким названием уже существует"
    do_query(format(edit_category_query, name, description, id_category))
    return "Данные изменены"

def delete_category(id_category):
    do_query(format(delete_category_query, id_category))
    return "Категория удалена"


def add_deposit_category(name, description=""):
    if search_category(name, get_deposit_categories()):
        return "Категория с таким названием уже существует"
    do_query(format(add_deposit_category_query, name, get_id_user(), description))
    return "Данные изменены"

def get_deposit_categories():
    return get_data(format(get_deposit_categories_query, get_id_user()))

def edit_deposit_category(id_deposit_category, name, description):
    ans = search_category(name, get_deposit_categories())
    if ans and ans != id_deposit_category:
        return "Категория с таким названием уже существует"
    do_query(format(edit_deposit_category_query, name, description, id_deposit_category))
    return "Данные изменены"

def delete_deposit_category(id_deposit_category):
    do_query(format(delete_deposit_category_query, id_deposit_category))
    return "Категория удалена"


def add_purchase(id_category, id_bank_account, sum, date, comment):
    #return format(get_current_sum_query, id_bank_account)
    ans = get_data(format(get_current_sum_query, id_bank_account))[0][0]
    sum = int(sum)
    if int(ans) < int(sum):
        return "Средств недостаточно"
    do_query(format(add_purchase_query, dt.now().strftime("%Y.%m.%d %H:%M:%S"),
                         id_category, id_bank_account, sum, date, comment))
    do_query(format(edit_sum_query, ans - sum, id_bank_account))
    return "Данные изменены"

def get_purchase():
    return get_data(format(get_purchases_query, get_id_user()))


def edit_purchase(id_purchase, id_category, id_bank_account, sum, date, comment):
    ans = get_data(format(get_current_sum_query, id_bank_account))[0][0]
    ans2 = get_data(format(get_sum_purchase_query, id_purchase))[0][0]
    if int(ans) < sum - ans2:
        return "Средств недостаточно"
    do_query(format(add_purchase_query, id_category, id_bank_account, sum, date, comment, id_purchase))
    do_query(format(edit_sum_query, id_bank_account, ans - (sum - ans2)))
    return "Данные изменены"

def delete_purchase(id_purchase):
    do_query(format(add_purchase_query, id_purchase))
    return "Данные изменены"


def add_bank_account(name, current_sum, description=""):
    if search_category(name, get_bank_accounts()):
        return "Категория с таким названием уже существует"
    do_query(format(add_bank_account_query, name, get_id_user(), current_sum, description))
    return "Данные изменены"

def get_bank_accounts():
    return get_data(format(get_bank_accounts_query, get_id_user()))

def edit_bank_account(id_bank_account, name, description=""):
    ans = search_category(name, get_bank_accounts())
    if ans and ans != id_bank_account:
        return "Счет с таким названием уже существует"
    #return format(edit_bank_account_query, name, description, id_bank_account)
    do_query(format(edit_bank_account_query, name, description, id_bank_account))
    return "Данные изменены"

def delete_bank_account(id_category):
    do_query(format(delete_bank_account_query, id_category))
    return "Счет удален"


def add_deposit(id_deposit_category, id_bank_account, sum, date, comment):
    sum = int(sum)
    ans = get_data(format(get_current_sum_query, id_bank_account))[0][0]
    do_query(format(add_deposit_query, dt.now().strftime("%Y.%m.%d %H:%M:%S"), id_deposit_category, id_bank_account, sum, date, comment))
    do_query(format(edit_sum_query, ans + sum, id_bank_account))
    #return format(edit_sum_query, id_bank_account, ans + sum)
    return "Данные изменены"

def get_deposits():
    return get_data(format(get_deposits_query, get_id_user()))

def edit_deposit(id_deposit, id_deposit_category, id_bank_account, sum, date, comment):
    ans = get_data(format(get_current_sum_query, id_bank_account))[0][0]
    ans2 = get_data(format(get_sum_deposit_query, id_deposit))[0][0]
    if ans < ans2 - sum:
        return "Средств недостаточно"
    do_query(format(add_deposit_query, id_deposit_category, id_bank_account, sum, date, comment,
                         id_deposit))
    do_query(format(edit_sum_query, id_bank_account, ans - (ans2 - sum)))
    return "Данные изменены"

def delete_deposit(id_deposit):
    do_query(format(add_deposit_query, id_deposit))
    return "Данные изменены"


def get_purchase_deposit(type, id):
    if type:
        return get_data(format(get_purchases_query2, id))
    else:
        return get_data(format(get_deposits_query2, id))

def get_sum():
    sum = 0

    for i in get_bank_accounts():
        sum += get_data(format(get_current_sum_query, i[0]))[0][0]
    return str(sum) + '₽'

def get_sum_deposits():
    sum = 0
    now = [int(i) for i in dt.now().strftime("%m.%Y").split(".")]
    for cat in get_deposit_categories():
        ds = get_data(format(get_deposits_query2, cat[0]))
        #return str(ds) + format(get_deposits_query2, cat[0])
        #return format(get_deposits_query2, get_id_user())
        for id, summa, date in ds:
            dd = [int(i) for i in date.split(".")][1:]
            if dd == now:
                sum += int(summa)
    return str(sum) + '₽'

def get_sum_purchases():
    sum = 0
    now = [int(i) for i in dt.now().strftime("%m.%Y").split(".")]
    for cat in get_categories():
        ds = get_data(format(get_purchases_query2, cat[0]))
        for id, summa, date in ds:
            dd = [int(i) for i in date.split(".")][1:]
            if dd == now:
                sum += int(summa)
    return str(sum) + '₽'
