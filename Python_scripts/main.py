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

        self.add_deposit_category_query = 'INSERT INTO deposit_categories(name, id_user, description) ' \
                                          'VALUES("{name}", {id_user}, "{description}")'
        self.get_deposit_categories_query = 'SELECT id_deposit_category, name, description FROM ' \
                                            'deposit_categories WHERE id_user={id_user}'
        self.is_there_deposit_category = 'SELECT id_deposit_category FROM deposit_categories WHERE name="{name}", ' \
                                         'id_user={id_user}'
        self.edit_deposit_category_query = 'UPDATE deposit_categories SET name="{name}", description="{description}" ' \
                                           'WHERE id_deposit_category={id_deposit_category}'
        self.delete_deposit_category_query = 'DELETE FROM deposit_categories WHERE ' \
                                             'id_deposit_category={id_deposit_category}'

        self.add_purchase_query = 'INSERT INTO purchases(date_time_add, id_category, id_bank_account, sum, date, ' \
                                  'comment) VALUES("{date_time_add}", {id_category}, {id_bank_account}, {sum}, ' \
                                  '"{date}", "{comment}")'
        self.get_purchases_query = 'SELECT purchases.id_purchase, purchases.id_category, purchases.id_bank_account, ' \
                                   'purchases.sum, purchases.date, purchases.comment FROM purchases ' \
                                   'INNER JOIN categories ON purchases.id_category = categories.id_category ' \
                                   'AND categories.id_user={id_user}'
        self.get_sum_purchase_query = 'SELECT sum FROM purchases WHERE purchase={purchase}'
        self.get_purchases_query2 = 'SELECT id_purchase, sum, date FROM ' \
                                    'purchases WHERE id_category={id_category}'
        self.edit_purchase_query = 'UPDATE purchases SET id_category={id_category}, id_bank_account={id_bank_account}' \
                                   ' sum={sum}, date="{date}", comment="{comment}" WHERE id_purchase={id_purchase}'
        self.delete_purchase_query = 'DELETE FROM purchases WHERE id_purchase={id_purchase}'

        self.add_bank_account_query = 'INSERT INTO bank_accounts(name, id_user, current_sum, description) ' \
                                      'VALUES("{name}", {id_user}, {current_sum}, "{description}")'
        self.get_bank_accounts_query = 'SELECT id_bank_account, name, current_sum, description FROM ' \
                                       'bank_accounts WHERE id_user={id_user}'
        self.is_there_bank_account = 'SELECT id_bank_account FROM bank_accounts WHERE name="{name}", id_user={id_user}'
        self.get_current_sum_query = 'SELECT current_sum FROM bank_accounts WHERE id_bank_account={id_bank_account}'
        self.edit_sum_query = 'UPDATE bank_accounts SET current_sum={current_sum} ' \
                              'WHERE id_bank_account={id_bank_account}'
        self.edit_bank_account_query = 'UPDATE bank_accounts SET name="{name}", description="{description}" ' \
                                       'current_sum={current_sum} WHERE id_bank_account={id_bank_account}'
        self.delete_bank_account_query = 'DELETE FROM bank_accounts WHERE id_bank_account={id_bank_account}'

        self.add_deposit_query = 'INSERT INTO deposits(date_time_add, id_deposit_category, id_bank_account, sum,' \
                                 'date, comment) VALUES("{date_time_add}", {id_deposit_category}, {id_bank_account}, ' \
                                 '{sum}, "{date}", "{comment}")'
        self.get_deposits_query = 'SELECT deposits.id_deposit, deposits.id_deposit_category, deposits.id_bank_account,' \
                                  ' deposits.sum, deposits.date, deposits.comment FROM deposits ' \
                                  'INNER JOIN deposit_categories ON deposits.id_deposit_category = ' \
                                  'deposit_categories.id_deposit_category AND deposit_categories.id_user={id_user}'
        self.get_sum_deposit_query = 'SELECT sum FROM deposits WHERE deposit={deposit}'
        self.get_deposits_query2 = 'SELECT id_deposit, sum, date FROM ' \
                                   'deposits WHERE id_deposit_category={id_deposit_category}'
        self.edit_deposit_query = 'UPDATE deposits SET id_deposit_category={id_deposit_category}, ' \
                                  'id_bank_account={id_bank_account} sum={sum}, date="{date}", comment="{comment}" ' \
                                  'WHERE id_deposit={id_deposit}'
        self.delete_deposit_query = 'DELETE FROM deposits WHERE id_deposit={id_deposit}'

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


    def add_deposit_category(self, name, description):
        ans = self.get_data(format(self.is_there_deposit_category, name, self.id_user))
        if ans:
            return "Категория с таким названием уже существует"
        self.do_query(format(self.add_deposit_category_query, name, self.id_user, description))
        return "Данные изменены"

    def get_deposit_categories(self):
        return self.get_data(format(self.get_deposit_categories_query, self.id_user))

    def edit_deposit_category(self, id_deposit_category, name, description):
        ans = self.get_data(format(self.is_there_deposit_category, name, self.id_user))
        if ans and ans[0][0] != id_deposit_category:
            return "Категория с таким названием уже существует"
        self.do_query(format(self.edit_deposit_category_query, name, description, id_deposit_category))
        return "Данные изменены"

    def delete_deposit_category(self, id_deposit_category):
        self.do_query(format(self.delete_deposit_category_query, id_deposit_category))
        raise Exception("Категория удалена")


    def add_purchase(self, id_category, id_bank_account, sum, date, comment):
        ans = self.get_data(format(self.get_current_sum_query, id_bank_account))[0][0]
        if ans < sum:
            return "Средств недостаточно"
        self.do_query(format(self.add_purchase_query, dt.now().strftime("%Y.%m.%d %H:%M:%S"),
                             id_category, id_bank_account, sum, date, comment))
        self.do_query(format(self.edit_sum_query, id_bank_account, ans - sum))
        return "Данные изменены"

    def get_purchase(self):
        return self.get_data(format(self.get_purchases_query, self.id_user))


    def edit_purchase(self, id_purchase, id_category, id_bank_account, sum, date, comment):
        ans = self.get_data(format(self.get_current_sum_query, id_bank_account))[0][0]
        ans2 = self.get_data(format(self.get_sum_purchase_query, id_purchase))[0][0]
        if ans < sum - ans2:
            return "Средств недостаточно"
        self.do_query(format(self.add_purchase_query, id_category, id_bank_account, sum, date, comment, id_purchase))
        self.do_query(format(self.edit_sum_query, id_bank_account, ans - (sum - ans2)))
        return "Данные изменены"

    def delete_purchase(self, id_purchase):
        self.do_query(format(self.add_purchase_query, id_purchase))
        return "Данные изменены"


    def add_bank_account(self, name, current_sum, description):
        ans = self.get_data(format(self.is_there_category, name, self.id_user))
        if ans:
            return "Категория с таким названием уже существует"
        self.do_query(format(self.add_category_query, name, self.id_user, current_sum, description))
        return "Данные изменены"

    def get_bank_accounts(self):
        return self.get_data(format(self.get_categories_query, self.id_user))

    def edit_bank_account(self, id_category, name, current_sum, description):
        ans = self.get_data(format(self.is_there_category, name, self.id_user))
        if ans and ans[0][0] != id_category:
            return "Категория с таким названием уже существует"
        self.do_query(format(self.edit_category_query, name, current_sum, description, id_category))
        return "Данные изменены"

    def delete_bank_account(self, id_category):
        self.do_query(format(self.delete_category_query, id_category))
        raise Exception("Категория удалена")


    def add_deposit(self, id_deposit_category, id_bank_account, sum, date, comment):
        ans = self.get_data(format(self.get_current_sum_query, id_bank_account))[0][0]
        if ans < sum:
            return "Средств недостаточно"
        self.do_query(format(self.add_deposit_query, dt.now().strftime("%Y.%m.%d %H:%M:%S"),
                             id_deposit_category, id_bank_account, sum, date, comment))
        self.do_query(format(self.edit_sum_query, id_bank_account, ans - sum))
        return "Данные изменены"

    def get_deposits(self):
        return self.get_data(format(self.get_deposits_query, self.id_user))

    def edit_deposit(self, id_deposit, id_deposit_category, id_bank_account, sum, date, comment):
        ans = self.get_data(format(self.get_current_sum_query, id_bank_account))[0][0]
        ans2 = self.get_data(format(self.get_sum_deposit_query, id_deposit))[0][0]
        if ans < ans2 - sum:
            return "Средств недостаточно"
        self.do_query(format(self.add_deposit_query, id_deposit_category, id_bank_account, sum, date, comment,
                             id_deposit))
        self.do_query(format(self.edit_sum_query, id_bank_account, ans - (ans2 - sum)))
        return "Данные изменены"

    def delete_deposit(self, id_deposit):
        self.do_query(format(self.add_deposit_query, id_deposit))
        return "Данные изменены"


    def get_purchase_deposit(self, type, id):
        if type:
            return self.get_data(format(self.get_purchases_query2, id))
        else:
            return self.get_data(format(self.get_deposits_query2, id))
