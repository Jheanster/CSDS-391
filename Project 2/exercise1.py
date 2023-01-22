import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import random
import itertools

iris = np.array(pd.DataFrame(pd.read_csv("P2/irisdata.csv"),columns=['petal_len', 'petal_w']).to_numpy())
m=iris.shape[0]

# computing the initial centroids randomly
K=3
# two arrays of size k to represent the x,y coordinates of the centroids
centroids=np.array([]).reshape(2,0) 
# creating K centroids randomly
for k in range(K):
    centroids=np.c_[centroids,iris[random.randint(0,m-1)]]

    output={}
# creating an empty array
euclid=np.array([]).reshape(m,0)
# finding distance between for each centroid
for k in range(K):
        dist=np.sum((iris-centroids[:,k])**2,axis=1)
        euclid=np.c_[euclid,dist]
# storing the minimum value we have computed
minimum=np.argmin(euclid,axis=1)+1
cent={}
for k in range(K):
    cent[k+1]=np.array([]).reshape(2,0)
# assigning of clusters to points
for k in range(m):
    cent[minimum[k]]=np.c_[cent[minimum[k]],iris[k]]
for k in range(K):
    cent[k+1]=cent[k+1].T
# computing mean and updating it
for k in range(K):
     centroids[:,k]=np.mean(cent[k+1],axis=0)
for i in range(50):
      euclid=np.array([]).reshape(m,0)
      for k in range(K):
          dist=np.sum((iris-centroids[:,k])**2,axis=1)
          euclid=np.c_[euclid,dist]
      C=np.argmin(euclid,axis=1)+1
      cent={}
      for k in range(K):
           cent[k+1]=np.array([]).reshape(2,0)
      for k in range(m):
           cent[C[k]]=np.c_[cent[C[k]],iris[k]]
      for k in range(K):
           cent[k+1]=np.transpose(cent[k+1])
           centroids[:,k]=np.mean(cent[k+1],axis=0)
      final=cent

def part_a_output():
    colors = itertools.cycle(["deepskyblue","darkseagreen","plum"])
    for k in range(K):

        plt.scatter(final[k+1][:,0],final[k+1][:,1],color=next(colors))
    plt.scatter(centroids[0],centroids[1],s=100,c='red',marker=",")
    plt.show()

def getTots(klusters):
    X =np.array(pd.DataFrame(iris,columns=['petal_len', 'petal_w']).to_numpy())
    # setting the number of training examples
    m=X.shape[0]

    K=klusters

    # creating an empty centroid array
    centroids=np.array([]).reshape(2,0) 

    # creating 5 random centroids
    for k in range(K):
        centroids=np.c_[centroids,X[random.randint(0,m-1)]]

    # creating an empty array
    euclid=np.array([]).reshape(m,0)

    # finding distance between for each centroid
    for k in range(K):
            dist=np.sum((X-centroids[:,k])**2,axis=1)
            euclid=np.c_[euclid,dist]


    # storing the minimum value we have computed
    minm = np.argmin(euclid,axis=1)

    tot = 0
    x = 0

    for i in minm:
        tot =  tot + euclid[x][i]
        x = x+1
        
    return tot


def part_b_output():

    listX = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    listY = []  
    for i in range(1,10):
        tot = 0
        for x in range(0, 100):
            tot = tot + getTots(i)
        listY.append(tot/100)

    plt.scatter(listX, listY, s=100,c='darkseagreen',marker=",")
    plt.title("Distortion vs. Clusters")
    plt.show()

# Helper method for part c, takes either 2 or 3 as input
def part_c(K):
    centroids=np.array([]).reshape(2,0) 

    # creating K centroids randomly
    for k in range(K):
        centroids=np.c_[centroids,iris[random.randint(0,m-1)]]
    centroidsOne = centroids
    plt.scatter(centroidsOne[0],centroidsOne[1],s=100, alpha=0.5, c='red', label='Initial')
    # creating an empty array
    euclid=np.array([]).reshape(m,0)
    # finding distance between for each centroid
    for k in range(K):
            dist=np.sum((iris-centroids[:,k])**2,axis=1)
            euclid=np.c_[euclid,dist]
    # storing the minimum value we have computed
    minimum=np.argmin(euclid,axis=1)+1
    cent={}
    for k in range(K):
        cent[k+1]=np.array([]).reshape(2,0)
    # assigning of clusters to points
    for k in range(m):
        cent[minimum[k]]=np.c_[cent[minimum[k]],iris[k]]
    for k in range(K):
        cent[k+1]=cent[k+1].T
    # computing mean and updating it
    for k in range(K):
         centroids[:,k]=np.mean(cent[k+1],axis=0)
    centroidsTwo = centroids
    #print(centroidsTwo)
    plt.scatter(centroidsTwo[0],centroidsTwo[1],s=100, alpha=0.5, c='blue', label='Intermediate')
    for i in range(50):
      euclid=np.array([]).reshape(m,0)
      for k in range(K):
          dist=np.sum((iris-centroids[:,k])**2,axis=1)
          euclid=np.c_[euclid,dist]
      C=np.argmin(euclid,axis=1)+1
      cent={}
      for k in range(K):
           cent[k+1]=np.array([]).reshape(2,0)
      for k in range(m):
           cent[C[k]]=np.c_[cent[C[k]],iris[k]]
      for k in range(K):
           cent[k+1]=np.transpose(cent[k+1])
           centroids[:,k]=np.mean(cent[k+1],axis=0)
      final=cent
    centroidsThree = centroids
    plt.scatter(centroidsThree[0],centroidsThree[1],s=100, alpha=0.5, c='green', label='Converged')
    plt.title(f"Clusters over time with k={K}")
    plt.legend()
    plt.ylim(0, 3)
    plt.xlim(0, 7)
    plt.show()

def part_c_output():
    part_c(2)
    part_c(3)
 
def part_d_output():
    xPts = np.sort(centroids[0])
    yPts = np.sort(centroids[1])
    x = np.linspace(1,7,5)

    colors = itertools.cycle(["deepskyblue","darkseagreen","plum"])
    for k in range(K):
        plt.scatter(final[k+1][:,0],final[k+1][:,1],color=next(colors))
    plt.scatter(centroids[0,:],centroids[1,:],s=100,c='red',marker=",")

    for i in range(K-1):
        midpt = [(xPts[i] + xPts[i+1])/2, (yPts[i] + yPts[i+1])/2]
        slope = -1/((yPts[i+1] - yPts[i])/(xPts[i+1] - xPts[i]))
        y = slope*x + (slope*-1*midpt[0] + midpt[1])
        plt.plot(x, y, 'black')
    plt.ylim(0, 3)
    plt.show()
