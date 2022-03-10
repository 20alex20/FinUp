import matplotlib.pyplot as plt
import datetime as dt
import numpy as np
from main import DB


def f(type: bool, time_mode: int, args, db):
    categories = db.get_categories()
    labels = []
    sums = []
    for id_category, name, description in categories:
        cursum = 0
        date_start = dt.date(*(list(args[:time_mode]) + [1] * (3 - time_mode)))
        date_stop = dt.date(*(list(args[time_mode:]) + [1] * (3 - time_mode)))
        for id_purchase, sum, date in db.get_purchase_deposit(type, id_category):
            if date_start <= dt.date(*map(int, date.split('.')[::-1])) < date_stop:
                cursum += sum
        labels.append(name)
        sums.append(cursum)
    return labels, sums


def dn(time_mode, date_start):
    if time_mode == 1:
        date_start = dt.date(date_start.year + 1, date_start.month, date_start.day)
    elif time_mode == 2:
        if date_start.month == 11:
            date_start = dt.date(date_start.year + 1, 1, date_start.day)
        else:
            date_start = dt.date(date_start.year, date_start.month + 1, date_start.day)
    else:
        date_start = date_start + dt.timedelta(days=1)
    return date_start


def f2(type: bool, time_mode: int, args, db):
    labels = []
    cs = {}
    categories = db.get_categories()
    for id_category, name, description in categories:
        cs[name] = []

    date_start = dt.date(*(list(args[:len(args) // 2]) + [1] * (3 - time_mode)))
    date_next = dn(time_mode, date_start)
    date_stop = dt.date(*(list(args[len(args) // 2:]) + [1] * (3 - time_mode)))
    while date_start < date_stop:
        labels.append('.'.join(date_start.strftime("%d.%m.%Y").split('.')[3 - time_mode:]))
        arggs = [date_start.year, date_start.month, date_start.day][:time_mode] + \
                [date_next.year, date_next.month, date_next.day][:time_mode]
        for label, sum in zip(*f(type, time_mode, arggs, db)):
            cs[label].append(sum)
        date_start = date_next
        date_next = dn(time_mode, date_start)
    return cs, labels


def graph(type: bool, mode: str, time_mode: int, *args):  # year_start, month_start, day_start, year_stop, month_stop, day_stop
    db = DB("db.sqlite3")
    print(db.login('jj', 'kk'))
    fig, ax = plt.subplots(figsize=(5, 2.7), layout='constrained')
    if mode == "plot":
        cs, labels = f2(type, time_mode, args, db)
        for category, sums in cs.items():
            ax.plot(np.arange(len(sums)), sums, label=category)
            ax.set_xticks(np.arange(len(sums)), labels=labels)

        ax.set_xlabel('Дата')
        ax.set_ylabel('Рубли')
        ax.set_title("График расходов за выбранный период")
        ax.legend()
    elif mode == 'pie':
        labels, sums = f(type, time_mode, args, db)
        colors = plt.get_cmap('Oranges')(np.linspace(0.2, 0.7, len(labels)))
        ax.pie(sums, colors=colors, radius=3, center=(4, 4), labels=labels,
               wedgeprops={"linewidth": 1, "edgecolor": "white"}, frame=True, autopct='%1.1f%%', shadow=True)
        ax.set_title('Процентное соотношение расходов по категориям')
        ax.set(xlim=(0, 8), xticks=range(1, 1), ylim=(0, 8), yticks=range(1, 1))
        ax.axis('off')
    elif mode == 'bar':
        labels, sums = f(type, time_mode, args, db)
        x = np.arange(len(labels))
        width = 0.35
        fig, ax = plt.subplots()
        rects2 = ax.bar(x, sums, width)
        ax.set_xlabel('Кактегории')
        ax.set_ylabel('Рубли')
        ax.set_xticks(x, labels)
        ax.set_title('Cуммы по категориям')
        ax.bar_label(rects2, padding=3)
    elif mode == 'bar2':
        cs, labels = f2(type, time_mode, args, db)
        ind = np.arange(len(tuple(cs.values())[0]))
        width = 0.35
        last = None
        for category, sums in cs.items():
            p = ax.bar(ind, sums, width, bottom=last, label=category)
            ax.bar_label(p, label_type='center')
            last = sums

        ax.axhline(0, color='grey', linewidth=0.8)
        ax.set_ylabel('Рубли')
        ax.set_xlabel('Дата')
        ax.set_title('Расходы за выбранный период')
        ax.set_xticks(ind, labels=labels)
        ax.legend()

    plt.savefig('example.png')  # io
    return


graph(True, "bar2", 2, 2020, 4, 2020, 6)
