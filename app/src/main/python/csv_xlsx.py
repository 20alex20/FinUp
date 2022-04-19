import csv
import xlsxwriter
from main import get_all_data
from main import environ


headers2 = {
    "categories": ("id_category", "name", "description"),
    "deposit_categories": ("id_deposit_category", "name", "description"),
    "purchases": ("id_purchase", "id_category", "id_bank_account", "sum", "date", "comment"),
    "bank_accounts": ("id_bank_account", "name", "current_sum", "description"),
    "deposits":  ("id_deposit", "id_category", "id_bank_account", "sum", "date", "comment"),
}
directory = environ["HOME"]

def export_csv(d=None):
    for name, cur_list in zip(sorted(headers2), get_all_data()):
        headers = headers2[name]
        with open(d + f'/export_data_FinUp_{name}.csv', 'w', newline='') as csvfile:
            writer = csv.writer(csvfile, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL)
            writer.writerow(headers)
            writer.writerows(cur_list)
    return "Осуществлен экспорт"


def export_xlsx(d=None):  # directory - Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    workbook = xlsxwriter.Workbook(d + '/export_data_FinUp.xlsx')
    for name, cur_list in zip(sorted(headers2), get_all_data()):
        headers = headers2[name]
        worksheet = workbook.add_worksheet()
        for column, i in enumerate(headers):
            worksheet.write(0, column, i)
        for row, i in enumerate(cur_list):
            for column, j in enumerate(i):
                worksheet.write(row + 1, column, j)
    workbook.close()
    return "Осуществлен экспорт"
