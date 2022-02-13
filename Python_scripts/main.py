import sqlite3
from datetime import datetime as dt
from werkzeug.security import generate_password_hash, check_password_hash


def format(string, *args):
    for i in args:
        ind1 = string.index('{')
        ind2 = string.index('}')
        string = string[:ind1] + str(i) + string[ind2 + 1:]
    return string


class DB:
    def __init__(self, name):
        self.bd_name = name
        self.register_query = 'INSERT INTO users(username_email, password, full_name) ' \
                              'VALUES("{username_email}", "{password_hash}", "{full_name}")'
        self.login_query = 'SELECT id_user, password_hash, full_name FROM ' \
                           'users WHERE username_email="{username_email}"'
        self.is_there_username_email = 'SELECT username_email FROM users ' \
                                       'WHERE username_email="{username_email}"'
        self.edit_about_me_query = 'UPDATE users SET username_email="{username_email}", full_name="{full_name}" ' \
                                   'WHERE id_user={id_user}'
        self.delete_my_account_query = 'DELETE FROM users WHERE id_user={id_user}'
        self.add_category_query = 'INSERT INTO categories(name, id_user, description) ' \
                                  'VALUES("{name}", {id_user}, "{description}")'
        self.get_categories_query = 'SELECT id_category, name, description FROM ' \
                                    'categories WHERE id_user={id_user}'
        self.is_there_category = 'SELECT id_category FROM categories WHERE name="{name}", id_user={id_user}'
        self.edit_category_query = 'UPDATE categories SET name="{name}", description="{description}" ' \
                                   'WHERE id_category={id_category}'
        self.delete_category_query = 'DELETE FROM categories WHERE id_category={id_category}'
        self.add_purchase_query = 'INSERT INTO purchases(date_time_add, id_category, sum, date, comment) ' \
                                  'VALUES("{date_time_add}", {id_category}, {sum}, "{date}", "{comment}")'
        self.get_purchases_query = 'SELECT purchases.id_purchase, purchases.id_category, purchases.sum, ' \
                                   'purchases.date, purchases.comment FROM purchases INNER JOIN categories ' \
                                   'ON purchases.id_category = categories.id_category ' \
                                   'AND categories.id_user={id_user}'
        self.edit_purchase_query = 'UPDATE purchases SET sum={sum}, date="{date}", comment="{comment}"' \
                                   'WHERE id_purchase={id_purchase}'
        self.delete_purchase_query = 'DELETE FROM purchases WHERE id_purchase={id_purchase}'
        self.id_user = None
        self.full_name = None
        self.username_email = None

    def get_data(self, query):  # для получения данных
        try:
            open(self.bd_name, 'rb').close()
        except FileNotFoundError:
            raise Exception("Файл не найден")
        connection = sqlite3.connect(self.bd_name)
        try:
            data = connection.cursor().execute(query).fetchall()
        except Exception as e:
            connection.close()
            return []
        connection.close()
        return data

    def do_query(self, query):  # для добавления/изменения данных
        try:
            open(self.bd_name, 'rb').close()
        except FileNotFoundError:
            raise Exception("Файл не найден")
        connection = sqlite3.connect(self.bd_name)
        try:
            connection.cursor().execute(query)
        except Exception:
            connection.close()
            raise Exception("В доступе отказано")
        connection.commit()
        connection.close()
        return "OK"

    def register(self, username_email, password, full_name):
        ans = self.get_data(format(self.is_there_username_email, username_email))
        if ans:
            return "Аккаунт на эту почту уже зарегистрирован"
        self.do_query(format(self.register_query, username_email, generate_password_hash(password), full_name))
        return self.login(username_email, password)

    def login(self, username_email, password):
        ans = self.get_data(format(self.login_query, username_email))
        if ans and check_password_hash(ans[0][1], password):
            self.id_user = ans[0][0]
            self.full_name = ans[0][2]
            self.username_email = username_email
            return "Вход разрешен"
        return "В доступе отказано"

    def get_full_name(self):
        return self.full_name

    def get_username_email(self):
        return self.username_email

    def edit_about_me(self, username_email, full_name):
        if username_email != self.username_email:
            ans = self.get_data(format(self.is_there_username_email, username_email))
            if ans:
                return "Аккаунт на эту почту уже зарегистрирован"
        self.do_query(format(self.edit_about_me_query, username_email, full_name, self.id_user))
        return "Данные изменены"

    def logout(self):
        self.id_user = None
        self.full_name = None
        self.username_email = None
        raise Exception("Выход произведен успешно")

    def delete_my_account(self):
        self.do_query(format(self.delete_my_account_query, self.id_user))
        try:
            self.logout()
        except Exception as e:
            raise Exception("Аккаунт удалён")

    def add_category(self, name, description):
        ans = self.get_data(format(self.is_there_category, name, self.id_user))
        if ans:
            return "Категория с таким названием уже существует"
        self.do_query(format(self.add_category_query, name, self.id_user, description))
        return "Данные изменены"

    def get_categories(self):
        return self.get_data(format(self.get_categories_query, self.id_user))

    def edit_category(self, id_category, name, description):
        ans = self.get_data(format(self.is_there_category, name, self.id_user))
        if ans and ans[0][0] != id_category:
            return "Категория с таким названием уже существует"
        self.do_query(format(self.edit_category_query, name, description, id_category))
        return "Данные изменены"

    def delete_category(self, id_category):
        self.do_query(format(self.delete_category_query, id_category))
        raise Exception("Категория удалена")

    def add_purchase(self, id_category, sum, date, comment):
        self.do_query(format(self.add_purchase_query, dt.now().strftime("%Y.%m.%d %H:%M:%S"),
                             id_category, sum, date, comment))
        return "Данные изменены"

    def get_purchase(self):
        return self.get_data(format(self.get_purchases_query, self.id_user))

    def edit_purchase(self, id_purchase, sum, date, comment):
        self.do_query(format(self.add_purchase_query, sum, date, comment, id_purchase))
        return "Данные изменены"

    def delete_purchase(self, id_purchase):
        self.do_query(format(self.add_purchase_query, id_purchase))
        return "Данные изменены"

