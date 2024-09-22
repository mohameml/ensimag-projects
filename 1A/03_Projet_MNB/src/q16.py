#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np



def K(n):
    # on calcul K tout d'abord 
    k=np.zeros((n,n))
    
    k[0][0]=2
    k[0][1],k[1][0]=-1,-1
    
    k[n-1][n-1]=1
    k[n-1][n-2],k[n-2][n-1]=-1,-1
    
    for i in range(1,n-1):
        k[i][i]=2
        k[i][i-1]= -1
        k[i][i+1]= -1
    
        
    return k


# question 16 
# la fonction fvec(u) return f(u)=-Ku³+e1
def fvec(u,n):
    "return f(u)"
    # valeur de k 
    k=K(n)
      
    #on calcule b = K*u^3
    u_3=(u*u)*u
    b=np.dot(k,u_3)
    
    #on definit e1
    e1=np.zeros(n)
    e1[0]=1
    
    # on return f(u)
    
    return (-1)*b+e1




if __name__=="__main__":
    
    # Test de la fonction fvec 
    u=np.array([2,-1,0]) # u=(1,0,0)
    
    print(fvec(u,3))