Логика программы разделена на backend и frontend. В качестве backend`а выступает десктопная версия программы, реализованная на языке Python.
Python-файлы, входящие в "backend", расположены в папке "Python_scripts" и включают в себя четыре файла, общим объёмом 483 строки кода:
1. main.py (298 строк кода)
   
   Данный файл предназначен для работы с базой данных. 
   Через функции get_data и do_query производятся запросы к базе данных db.sqlite3. 
   Помимо двух основных функций реализовано 26 вспомогательных функций для выполнения конкретных запросов к базе данных. 
   
   Перечень вспомогательных функций:
   register, login, edit_about_me, logout, delete_my_account, add_category, get_categories, edit_category, delete_category, add_deposit_category, get_deposit_categories, edit_deposit_category, delete_deposit_category, add_purchase, get_purchase, edit_purchase, delete_purchase, add_bank_account, get_bank_accounts, edit_bank_account, delete_bank_account, add_deposit, get_deposits, edit_deposit, delete_deposit, get_purchase_deposit

2. user.py (46 строк кода)
   
   Предназначен для тестирования работы вспомогательных функций из main.py через консоль.

3. csv_xlsx.py (26 строк кода)

   Данный файл предназначен для экспорта данных в xlsx (функция export_xlsx) и csv (функция export_csv). 

4. grafs.py (113 строк кода)

   Данный файл предназначен для формирования наглядных графиков. 
   Основной функцией является функция "graph", которая строит графики четырёх разных типов (круговая диаграмма, столбчатая диаграмма, гистограмма с накоплением, график с маркерами).
   За тип графика отвечает параметр "mode", параметр "type" - флаг, обозначающий построение графиков расходов (значение True) или графика доходов (значение False).
   Параметр "time_mode" отвечает за выбор построения по годам/месяцам/дням (значения 1, 2, 3 соответственно). 
   Параметр "args" служит для передачи даты старта и даты конца периода, за который требуется построить график.
