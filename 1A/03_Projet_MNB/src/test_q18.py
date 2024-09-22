#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np







#  on definit notre matrrice A

def resoudre(L,b):
    "prend en parametres :  n,mi,h et un veteur b "
    "return la solution de Ax=b"
    # on resoudre Ly=b "descente"
    n=len(L)+1
    sol_des=np.zeros(n) # sol_des reprenste y 
    sol_des[0]=b[0]
    for k in range(1,n):
        sol_des[k]=b[k]-L[k-1]*sol_des[k-1]
        
    return sol_des


L=np.array([1,2])
b=np.array([1,2,3])
print(resoudre(L,b))

