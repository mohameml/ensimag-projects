#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np

from q16 import K 

"""
on implement l'Algo de la factorisation de chloskey car 
A est symétrique definie positive
 et apres T=LD*(1/2)
"""
def factorise(n,mi,h):
    
    # on definit A tout d'abord A = I +(h/2)C avec C=miK
    
    I=np.identity(n)
    C=mi*K(n)
    A=I+(h/2)*C
    
    
    # on calcul tout d'abord T ta A=TT^t c
    T=np.zeros((n,n)) 
    for j in range(0,n):
        T[j][j]=np.sqrt((A[j][j])-(sum([(T[j][k])**2 for k in range(0,j)])))
        for i in range(j+1,n):
            T[i][j]=(1/T[j][j])*(A[i][j]-sum([T[i][k]*T[j][k] for k in range(0,j)]))
                           
    # on calcul D :D[i][i]=(T[i][i])**2
    D=np.zeros(n)
    for j in range(0,n):
        D[j]=(T[j][j])**2
    
    # on calcul L on a L[i][i-1]=T[i][i-1]/((D[i-1][i-1])**(1/2))
    L=np.zeros(n-1)
    for i in range(0,n-1):
        L[i]=T[i+1][i]/(T[i][i])
                           
    # on return les deux vecteures L et D :
    return L,D  


if __name__=="__main__":
    print(factorise(4,1,2))