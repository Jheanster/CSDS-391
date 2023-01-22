import exercise1
import exercise2
import exercise3
import exercise4
import numpy as np
import data_generator

m = np.array([0.6, 1.8])
b = -5.73
w = np.array([b, m[0], m[1]]).T

# importing the data from the iris.csv file:
sepal_length, sepal_width, petal_len, petal_w, species = data_generator.iris_data_generator('P2/irisdata.csv')

# making a 2x100 matrix of the petal length and width data vectors:
X = data_generator.create_data_vectors(petal_len, petal_w)

# making a 3x100 augmented matrix with all ones in row 0, and petal length and width in rows 1 and 2
X_augmented = data_generator.create_augmented_data_vectors(petal_len, petal_w)

# HyperParameters
step_size = 0.0025

# Exercise 1: 
def output_1a():
    exercise1.part_a_output()

def output_1b():
    exercise1.part_b_output()

def output_1c():
    exercise1.part_c_output()

def output_1d():
    exercise1.part_d_output()

# Exercise 2:
def output_2c():
    exercise2.plot_iris_data(petal_len, petal_w, species, m=m, b=b,boundaries=True)

def output_2d():
    exercise2.surface_plot_input_space(m, b)



# Exercise 3:
def output_3b():
    mean_squared_error = exercise3.mean_squared_error(X, m, b, species)
    print(mean_squared_error)
    m_bad = np.array([1.0, 2.1])
    b_bad = -6
    exercise3.plot_iris_data_with_two_decision_boundaries(petal_len, petal_w, species, m, b, m_bad, b_bad)
    mean_squared_error = exercise3.mean_squared_error(X, m_bad, b_bad, species)
    print(mean_squared_error)


def output_3e():
    new_weight = np.copy(w)
    print(new_weight)
    exercise2.plot_iris_data(petal_len, petal_w, species, subtitle="First", w=new_weight)
    d_w = exercise3.gradient_mean_squared_error(X_augmented, new_weight, species)
    new_weight = new_weight - step_size*d_w
    print(new_weight)
    exercise2.plot_iris_data(petal_len, petal_w, species, subtitle="Second", w=new_weight)

# Exercise 4:
def output_4a():
    iris_classifier = exercise2.simple_classifier(w=w)
    print(iris_classifier.get_weights())
    final_weight = exercise4.optimize_boundary(iris_classifier, step_size, X_augmented, petal_len, petal_w, species,
                                 progress_output=True)
    print(final_weight)


def output_4c():
    random_weight = np.array([-12.82893561,   1.05314637,   4.67818169])
    print("Starting Vector:")
    print(random_weight)
    print("MSE:")
    print(exercise3.mean_squared_error(X, random_weight[1:3], random_weight[0], species))
    iris_classifier = exercise2.simple_classifier(w=random_weight)
    print(iris_classifier.get_weights())
    final_weight = exercise4.optimize_boundary(iris_classifier, step_size, X_augmented, petal_len, petal_w, species,
                                 progress_output=True)
    print(final_weight)


# Put commands here:
# output_1a()
# output_1b()

# output_1c()

# output_1d()

# output_2c()

# output_2d()

# output_2e()

# output_3b()

# output_3e()

# output_4a()

# output_4c()
