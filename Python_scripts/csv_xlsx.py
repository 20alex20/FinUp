import csv
import xlsxwriter


def export_csv(directory: str, headers: list, cur_list: list):
    with open(directory + '/export_data_FinUp.csv', 'w', newline='') as csvfile:
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


if __name__ == '__main__':
    export_csv(".", ["gvghvgh", "kjhuijh", "bhjbgkjui"], [[1, 2, 3], [1, 2, 4], [1, 2, 5]])
    export_xlsx(".", ["gvghvgh", "kjhuijh", "bhjbgkjui"], [[1, 2, 3], [1, 2, 4], [1, 2, 5]])
