#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np
from q7 import iter 

# question 8
def solnum(u0,v0,T,h):
    kmax=int(T/h)
    matrice=np.zeros((2,kmax+1))
    matrice[0][0]=u0
    matrice[1][0]=v0
    for i in range(1,kmax+1):
        valeur=iter(u0,v0,h)
        matrice[0][i]=valeur[0]
        matrice[1][i]=valeur[1]
        u0,v0=valeur[0],valeur[1]
    return matrice




if __name__=="__main__":
    
        #test de solnum
        u=1
        c=0.3
        v=c
        T=1
        h=10**(-1)
        matrice=solnum(u,v,T,h)
        print(f'l\'approximation de u,v aux pointx tk=kh avec les conditions initiales (u*,v*) est {matrice}  ' )

