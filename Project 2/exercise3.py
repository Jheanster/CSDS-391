import numpy as np
import matplotlib.pyplot as plt
import math

# Functions written in Part 3:

def mean_squared_error(X, m, b, species):

    mean_squared_error_sum = 0
    for i in range(len(X[0, :])):
        
        x_one = X[0, i]
        x_two = X[1, i]
        y = b + m[0]*x_one + m[2]*x_two
        prediction = 1 / (1 + np.exp(-y))
        ground_truth = None
        if species[i] == "versicolor":
            ground_truth = 0
        elif species[i] == "virginica":
            ground_truth = 1
        mean_squared_error_sum += (ground_truth - prediction)**2

    return mean_squared_error_sum/len(X[0, :])


def gradient_mean_squared_error(X_augmented, w, species):
    gradient_sum = np.zeros(3)
    for i in range(len(X_augmented[0, :])):
        x_vector = X_augmented[:, i]  # column data vector
        y = None
        if species[i] == "versicolor":
            y = 0.0
        elif species[i] == "virginica":
            y = 1.0

        sigmoid = 1 / (1 + math.exp(-np.dot(w, x_vector)))
        # print(-np.dot(w, x_vector))
        gradient_sum[0] += -2 * (y - sigmoid) * (sigmoid * (1 - sigmoid)) * x_vector[0]
        gradient_sum[1] += -2 * (y - sigmoid) * (sigmoid * (1 - sigmoid)) * x_vector[1]
        gradient_sum[2] += -2 * (y - sigmoid) * (sigmoid * (1 - sigmoid)) * x_vector[2]
    return gradient_sum


def plot_iris_data_with_two_decision_boundaries(petal_len, petal_w, species, m_one, b_one, m_two, b_two):
    versicolor_petal_len = []
    versicolor_petal_w = []
    virginica_petal_len = []
    virginica_petal_w = []
    for l, w, s in zip(petal_len, petal_w, species):
        if s == 'versicolor':
            versicolor_petal_len.append(l)
            versicolor_petal_w.append(w)
        elif s == 'virginica':
            virginica_petal_len.append(l)
            virginica_petal_w.append(w)
    # drawing the line
    x_ones = np.linspace(0, 7.5, 75)
    x_twos_one = []
    x_twos_two = []
    for x_one in x_ones:
        x_twos_one.append(-(m_one[0]/m_one[1])*x_one-(b_one/m_one[1]))
        x_twos_two.append(-(m_two[0]/m_two[1])*x_one-(b_two/m_two[1]))
        

    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.xaxis.set_ticks_position('bottom')
    ax.yaxis.set_ticks_position('left')

    
    ax.scatter(versicolor_petal_len, versicolor_petal_w, color='teal', label='Versicolor')
    ax.scatter(virginica_petal_len, virginica_petal_w, color='lightcoral', label='Virginica')
    plt.plot(x_ones, x_twos_one, color='black')
    plt.plot(x_ones, x_twos_two, color='red')

    plt.title("Iris Data")
    plt.ylabel("Petal Width (cm) [x\u2082]")
    plt.xlabel("Petal Length (cm) [x\u2081]")
    plt.xlim(0, 7.5)
    plt.ylim(0.8, 2.6)
    plt.legend()
    plt.show()
