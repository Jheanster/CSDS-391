

import numpy as np
import matplotlib.pyplot as plt
import matplotlib as mpl

# Functions written in Exercise 2:

class decision_boundary:
    def __init__(self, **kwargs):
        self.w = np.zeros(3)
        if len(kwargs) == 2:
            self.w[0] = kwargs['b']
            self.w[1:3] = kwargs['m']
        elif len(kwargs) == 1:
            self.w = kwargs['w']
        

    def get_x_two(self, x_one):
        x_two = -(self.w[1]/self.w[2])*x_one-(self.w[0]/self.w[2])
        return x_two

def plot_iris_data(petal_len, petal_w, species, subtitle=None, boundaries=True, **kwargs ):
    w = np.zeros(3)
    if len(kwargs) == 2:  
        w[0] = kwargs['b']
        w[1:3] = kwargs['m']
    elif len(kwargs) == 1:
        w = kwargs['w']
    

    versicolor_petal_len = []
    versicolor_petal_w = []
    virginica_petal_len = []
    virginica_petal_w = []
    for p_l, p_w, s in zip(petal_len, petal_w, species):
        if s == 'versicolor':
            versicolor_petal_len.append(p_l)
            versicolor_petal_w.append(p_w)
        elif s == 'virginica':
            virginica_petal_len.append(p_l)
            virginica_petal_w.append(p_w)
            
    # drawing the boundary if needed
    if boundaries == True:
        x_ones = np.linspace(0, 7.5, 75)
        x_twos = []
        iris_decision_boundary = decision_boundary(w=w)
        for x_one in x_ones:
            x_twos.append(iris_decision_boundary.get_x_two(x_one))

    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.xaxis.set_ticks_position('bottom')
    ax.yaxis.set_ticks_position('left')
    ax.scatter(versicolor_petal_len, versicolor_petal_w, color='teal', label='Versicolor')
    ax.scatter(virginica_petal_len, virginica_petal_w, color='lightcoral', label='Virginica')
    if boundaries == True:
        plt.plot(x_ones, x_twos, color='black')
    if subtitle == None:
        plt.title("Iris Data")
    else:
        plt.title("Iris Data: {st}".format(st=subtitle))
    plt.ylabel("Petal Width (cm) [x\u2082]")
    plt.xlabel("Petal Length (cm) [x\u2081]")
    plt.xlim(0, 7.5)
    plt.ylim(0.8, 2.6)
    plt.legend()
    plt.show()


class simple_classifier:
    def __init__(self, **kwargs):  # init with either (w) or (m and b)
        self.w = np.zeros(3)
        if len(kwargs) == 2:
            self.w[0] = kwargs['b']
            self.w[1:3] = kwargs['m']
        elif len(kwargs) == 1:
            self.w = kwargs['w']
       

    def classify(self, x_one, x_two):
        y = self.w[0] + self.w[1]*x_one + self.w[2]*x_two
        sigmoid = 1 / (1 + np.exp(-y))
        return sigmoid

    def get_weights(self):
        return self.w

    def set_weights(self, w):
        self.w = w


def surface_plot_input_space(m, b):
    x_one = np.linspace(0, 7, num=70+1)  # Petal Length
    x_two = np.linspace(0, 2.6, num=26+1)  # Petal Width

    fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
    x_one, x_two = np.meshgrid(x_one, x_two)
    iris_data_classifier = simple_classifier(m=m, b=b)
    sigmoid = iris_data_classifier.classify(x_one, x_two)
    ax.plot_surface(x_one, x_two, sigmoid, cmap=mpl.cm.spring, linewidth=0, antialiased=True)
    ax.set_xlim(0, 7.0)
    ax.set_ylim(0, 2.6)
    ax.set_zlim(0, 1.0)
    ax.set_xlabel("Petal Length (cm) [x\u2081]")
    ax.set_ylabel("Petal Width (cm) [x\u2082]")
    ax.set_zlabel("Sigmoid Value")
    ax.view_init(elev=15., azim=-50)

    plt.show()

