from main import DB


db = DB("db.sqlite3")
functions = {
    "register": (db.register, "username_email, password, full_name"),
    "login": (db.login, "username_email, password"),
    "get_full_name": (db.get_full_name, ""),
    "get_username_email": (db.get_username_email, ""),
    "edit_about_me": (db.edit_about_me, "username_email, full_name"),
    "logout": (db.logout, ""),
    "delete_my_account": (db.delete_my_account, ""),
    "add_category": (db.add_category, "name, description"),
    "get_categories": (db.get_categories, ""),
    "edit_category": (db.edit_category, "id_category, name, description"),
    "delete_category": (db.delete_category, "id_category"),
    "add_purchase": (db.add_purchase, "id_category, sum, date, comment"),
    "get_purchase": (db.get_purchase, ""),
    "edit_purchase": (db.edit_purchase, "id_purchase, sum, date, comment"),
    "delete_purchase": (db.delete_purchase, ""),
    "sort_last": ""
}
print("Start console...")
command = input()
last = None
while command in functions:
    if command == "sort_last":
        n = int(input("Введите номер столбца сортировки: "))
        reverse = input("Введите тип сортировки (прямая/обратная): ") == "обратная"
        last = sorted(last, key=lambda x: x[n - 1], reverse=reverse)
        print(last)
        command = input()
        continue
    args = functions[command][1]
    if args:
        print("Args:", args)
    try:
        if args:
            last = functions[command][0](*input().split(', '))
        else:
            last = functions[command][0]()
        print(last)
    except Exception as e:
        print("В доступе отказано", e)
    command = input()
