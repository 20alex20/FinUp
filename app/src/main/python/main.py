import sqlite3
from datetime import datetime as dt
from werkzeug.security import generate_password_hash, check_password_hash


def format(string, *args):
    for i in args:
        ind1 = string.index('{')
        ind2 = string.index('}')
        string = string[:ind1] + str(i) + string[ind2 + 1:]
    return string

def get_id_user():
    with open("id_user.txt", "r") as f:
        return int(f.read().split('.')[0])

def get_full_name():
    with open("id_user.txt", "r") as f:
        return f.read().split('.')[2]

def get_username_email():
    with open("id_user.txt", "r") as f:
        return f.read().split('.')[1]

def write_all(first, second, third):
    first = str(first)
    with open("id_user.txt", "w+") as f:
        f.write(first + second + third)


register_query = 'INSERT INTO users(username_email, password, full_name) ' \
                      'VALUES("{username_email}", "{password_hash}", "{full_name}")'
login_query = 'SELECT id_user, password_hash, full_name FROM ' \
                   'users WHERE username_email="{username_email}"'
is_there_username_email = 'SELECT username_email FROM users ' \
                               'WHERE username_email="{username_email}"'
edit_about_me_query = 'UPDATE users SET username_email="{username_email}", full_name="{full_name}" ' \
                           'WHERE id_user={id_user}'
delete_my_account_query = 'DELETE FROM users WHERE id_user={id_user}'

add_category_query = 'INSERT INTO categories(name, id_user, description) ' \
                          'VALUES("{name}", {id_user}, "{description}")'
get_categories_query = 'SELECT id_category, name, description FROM ' \
                            'categories WHERE id_user={id_user}'
is_there_category = 'SELECT id_category FROM categories WHERE name="{name}", id_user={id_user}'
edit_category_query = 'UPDATE categories SET name="{name}", description="{description}" ' \
                           'WHERE id_category={id_category}'
delete_category_query = 'DELETE FROM categories WHERE id_category={id_category}'

add_deposit_category_query = 'INSERT INTO deposit_categories(name, id_user, description) ' \
                                  'VALUES("{name}", {id_user}, "{description}")'
get_deposit_categories_query = 'SELECT id_deposit_category, name, description FROM ' \
                                    'deposit_categories WHERE id_user={id_user}'
is_there_deposit_category = 'SELECT id_deposit_category FROM deposit_categories WHERE name="{name}", ' \
                                 'id_user={id_user}'
edit_deposit_category_query = 'UPDATE deposit_categories SET name="{name}", description="{description}" ' \
                                   'WHERE id_deposit_category={id_deposit_category}'
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
                            'purchases WHERE id_category={id_category}'
edit_purchase_query = 'UPDATE purchases SET id_category={id_category}, id_bank_account={id_bank_account}' \
                           ' sum={sum}, date="{date}", comment="{comment}" WHERE id_purchase={id_purchase}'
delete_purchase_query = 'DELETE FROM purchases WHERE id_purchase={id_purchase}'

add_bank_account_query = 'INSERT INTO bank_accounts(name, id_user, current_sum, description) ' \
                              'VALUES("{name}", {id_user}, {current_sum}, "{description}")'
get_bank_accounts_query = 'SELECT id_bank_account, name, current_sum, description FROM ' \
                               'bank_accounts WHERE id_user={id_user}'
is_there_bank_account = 'SELECT id_bank_account FROM bank_accounts WHERE name="{name}", id_user={id_user}'
get_current_sum_query = 'SELECT current_sum FROM bank_accounts WHERE id_bank_account={id_bank_account}'
edit_sum_query = 'UPDATE bank_accounts SET current_sum={current_sum} ' \
                      'WHERE id_bank_account={id_bank_account}'
edit_bank_account_query = 'UPDATE bank_accounts SET name="{name}", description="{description}" ' \
                               'current_sum={current_sum} WHERE id_bank_account={id_bank_account}'
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
                           'deposits WHERE id_deposit_category={id_deposit_category}'
edit_deposit_query = 'UPDATE deposits SET id_deposit_category={id_deposit_category}, ' \
                          'id_bank_account={id_bank_account} sum={sum}, date="{date}", comment="{comment}" ' \
                          'WHERE id_deposit={id_deposit}'
delete_deposit_query = 'DELETE FROM deposits WHERE id_deposit={id_deposit}'

bd_name = "db.sqlite3"


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
    try:
        connection.cursor().execute(query)
    except Exception:
        connection.close()
        raise Exception("В доступе отказано")
    connection.commit()
    connection.close()
    return "OK"


def register(username_email, password, full_name):
    with open("D:/hh.txt", 'w+') as f:
        f.write(username_email + ' ' + password + ' ' + full_name)
    return  'k'
    # ans = get_data(format(is_there_username_email, username_email))
    # if ans:
    #     return "Аккаунт на эту почту уже зарегистрирован"
    # do_query(format(register_query, username_email, generate_password_hash(password), full_name))
    # return login(username_email, password)

def login(username_email, password):
    ans = get_data(format(login_query, username_email))
    if ans and check_password_hash(ans[0][1], password):
        id_user = ans[0][0]
        full_name = ans[0][2]
        write_all(id_user, full_name, username_email)
        return "Вход разрешен"
    return "В доступе отказано"


def edit_about_me(username_email, full_name):
    if username_email != get_username_email():
        ans = get_data(format(is_there_username_email, username_email))
        if ans:
            return "Аккаунт на эту почту уже зарегистрирован"
    do_query(format(edit_about_me_query, username_email, full_name, get_id_user()))
    return "Данные изменены"

def logout():
    with open("id_user.txt") as f:
        f.write("")
    raise Exception("Выход произведен успешно")

def delete_my_account():
    do_query(format(delete_my_account_query, get_id_user()))
    try:
        logout()
    except Exception as e:
        raise Exception("Аккаунт удалён")


def add_category(name, description):
    ans = get_data(format(is_there_category, name, get_id_user()))
    if ans:
        return "Категория с таким названием уже существует"
    do_query(format(add_category_query, name, get_id_user(), description))
    return "Данные изменены"

def get_categories():
    return get_data(format(get_categories_query, get_id_user()))

def edit_category(id_category, name, description):
    ans = get_data(format(is_there_category, name, get_id_user()))
    if ans and ans[0][0] != id_category:
        return "Категория с таким названием уже существует"
    do_query(format(edit_category_query, name, description, id_category))
    return "Данные изменены"

def delete_category(id_category):
    do_query(format(delete_category_query, id_category))
    raise Exception("Категория удалена")


def add_deposit_category(name, description):
    ans = get_data(format(is_there_deposit_category, name, get_id_user()))
    if ans:
        return "Категория с таким названием уже существует"
    do_query(format(add_deposit_category_query, name, get_id_user(), description))
    return "Данные изменены"

def get_deposit_categories():
    return get_data(format(get_deposit_categories_query, get_id_user()))

def edit_deposit_category(id_deposit_category, name, description):
    ans = get_data(format(is_there_deposit_category, name, get_id_user()))
    if ans and ans[0][0] != id_deposit_category:
        return "Категория с таким названием уже существует"
    do_query(format(edit_deposit_category_query, name, description, id_deposit_category))
    return "Данные изменены"

def delete_deposit_category(id_deposit_category):
    do_query(format(delete_deposit_category_query, id_deposit_category))
    raise Exception("Категория удалена")


def add_purchase(id_category, id_bank_account, sum, date, comment):
    ans = get_data(format(get_current_sum_query, id_bank_account))[0][0]
    if ans < sum:
        return "Средств недостаточно"
    do_query(format(add_purchase_query, dt.now().strftime("%Y.%m.%d %H:%M:%S"),
                         id_category, id_bank_account, sum, date, comment))
    do_query(format(edit_sum_query, id_bank_account, ans - sum))
    return "Данные изменены"

def get_purchase():
    return get_data(format(get_purchases_query, get_id_user()))


def edit_purchase(id_purchase, id_category, id_bank_account, sum, date, comment):
    ans = get_data(format(get_current_sum_query, id_bank_account))[0][0]
    ans2 = get_data(format(get_sum_purchase_query, id_purchase))[0][0]
    if ans < sum - ans2:
        return "Средств недостаточно"
    do_query(format(add_purchase_query, id_category, id_bank_account, sum, date, comment, id_purchase))
    do_query(format(edit_sum_query, id_bank_account, ans - (sum - ans2)))
    return "Данные изменены"

def delete_purchase(id_purchase):
    do_query(format(add_purchase_query, id_purchase))
    return "Данные изменены"


def add_bank_account(name, current_sum, description):
    ans = get_data(format(is_there_category, name, get_id_user()))
    if ans:
        return "Категория с таким названием уже существует"
    do_query(format(add_category_query, name, get_id_user(), current_sum, description))
    return "Данные изменены"

def get_bank_accounts():
    return get_data(format(get_categories_query, get_id_user()))

def edit_bank_account(id_category, name, current_sum, description):
    ans = get_data(format(is_there_category, name, get_id_user()))
    if ans and ans[0][0] != id_category:
        return "Категория с таким названием уже существует"
    do_query(format(edit_category_query, name, current_sum, description, id_category))
    return "Данные изменены"

def delete_bank_account(id_category):
    do_query(format(delete_category_query, id_category))
    raise Exception("Категория удалена")


def add_deposit(id_deposit_category, id_bank_account, sum, date, comment):
    ans = get_data(format(get_current_sum_query, id_bank_account))[0][0]
    if ans < sum:
        return "Средств недостаточно"
    do_query(format(add_deposit_query, dt.now().strftime("%Y.%m.%d %H:%M:%S"),
                         id_deposit_category, id_bank_account, sum, date, comment))
    do_query(format(edit_sum_query, id_bank_account, ans - sum))
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