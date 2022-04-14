import csv
import xlsxwriter


headers2 = {
    "categories": ("id_category", "name", "description"),
    "deposit_categories": ("id_deposit_category", "name", "description"),
    "purchases": ("id_purchase", "sum", "date"),
    "bank_accounts": ("id_bank_account", "name", "current_sum", "description"),
    "deposits":  ("id_deposit", "sum", "date")
}

def export_csv(directory: str):

    for name in sorted(headers2):
        headers = headers2[name]
        with open(directory + f'/export_data_FinUp_{name}.csv', 'w', newline='') as csvfile:
            writer = csv.writer(csvfile, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL)
            writer.writerow(headers)
            writer.writerows(cur_list)


def export_xlsx(directory: str, headers: list, cur_list: list):  # directory - Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    workbook = xlsxwriter.Workbook(directory + '/export_data_FinUp.xlsx')
    worksheet = workbook.add_worksheet()
    for column, i in enumerate(headers):
        worksheet.write(0, column, i)
    for row, i in enumerate(cur_list):
        for column, j in enumerate(i):
            worksheet.write(row + 1, column, j)
    workbook.close()


export_csv(".", ["gvghvgh", "kjhuijh", "bhjbgkjui"], [[1, 2, 3], [1, 2, 4], [1, 2, 5]])
export_xlsx(".", ["gvghvgh", "kjhuijh", "bhjbgkjui"], [[1, 2, 3], [1, 2, 4], [1, 2, 5]])
