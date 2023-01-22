import exercise2
import exercise3

import numpy as np
import matplotlib.pyplot as plt
import math


def optimize_boundary(classifier, step_size, X_augmented, petal_len, petal_w, species, progress_output=False):
    threshold = 0.25
    gradient = 0

    # Parameters for Graphing/Output:
    num_iterations = 0
    weights_store = []
    mse_store = []
    while True:
        num_iterations += 1  # for graphing
        gradient = exercise3.gradient_mean_squared_error(X_augmented, classifier.get_weights(), species)
        new_weights = classifier.get_weights()-step_size*gradient
        if progress_output:
            mse_store.append(exercise3.mean_squared_error(X_augmented[1:3, :], new_weights[1:3], new_weights[0], species))  # for graphing
        weights_store.append(new_weights)  # for graphing
        classifier.set_weights(new_weights)
        if np.linalg.norm(gradient) < threshold:  # convergence condition
            break
    print(num_iterations)
    if progress_output:  # is true
        plot_locations = {'Initial': 0, 'Middle': math.floor(num_iterations/2), 'Final': num_iterations-1}
        for key in plot_locations:
            exercise2.plot_iris_data(petal_len, petal_w, species, subtitle=key,
                                                               w=weights_store[plot_locations[key]])
            if key != 'Initial':
                plot_loss_over_iterations(mse_store[0:plot_locations[key]], plot_locations[key], subtitle=key)

    return classifier.get_weights()


def plot_loss_over_iterations(mse_store, num_iterations, subtitle = None):
    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.xaxis.set_ticks_position('bottom')
    ax.yaxis.set_ticks_position('left')
    plt.plot(range(1, num_iterations+1), mse_store, color='indigo')
    if subtitle == None:
        plt.title("Mean Squared Error (Loss) vs. Num. Iterations")
    else:
        plt.title("Mean Squared Error (Loss) vs. Num. Iterations: {st}".format(st=subtitle))
    plt.ylabel("Mean Squared Error")
    plt.xlabel("Number of Iterations")
    # plt.xlim(0, 7.5)
    plt.show()
