import matplotlib.pyplot as plt
import io


def main(number1, number2):

    num1 = int(number1)
    num2 = int(number2)
    # summ = num1 + num2

    xa = [float(word) for word in range(num1)]
    ya = [float(word) for word in range(num2)]
    fig, ax = plt.subplots()
    ax.plot(xa, ya)
    f = io.BytesIO()
    plt.savefig(f, format="png")
    return f.getvalue()

    #plt.style.use('_mpl-gallery')
    #x = np.linspace(0, 10, 100)
    #y = 4 + 2 * np.sin(2 * x)
    #fig, ax = plt.subplots()
    #ax.plot(x, y, linewidth=2.0)
    #ax.set(xlim=(0, 8), xticks=np.arange(1, 8),
    #       ylim=(0, 8), yticks=np.arange(1, 8))
    #plt.savefig('../res/drawable/img.png')

    #return "Сумма: " + str(summ)
