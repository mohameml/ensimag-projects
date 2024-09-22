#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np

from q8 import solnum


# question 9

c=0.3
r=-c/2
s=(np.sqrt(4-c**2))/2
b=-c/(np.sqrt(4-c**2))


def u(t):
    
    return (np.exp(r*t))*(-(np.cos(s*t))+(np.sin(s*t))*b)+1

def v(t):
    d=(2-c**2)/(np.sqrt(4-c**2))
     
    
    return (np.exp(r*t))*(d*(np.sin(s*t))-c*(np.cos(s*t))) +c  
    

def solexacte(t):
    
    kmax=len(t)-1
    matrice=np.zeros((2,kmax+1)) 
    
    for i in range(0,kmax+1):
        a=u(t[i])
        b=v(t[i])
        matrice[0][i]=a
        matrice[1][i]=b
        
    return matrice



if __name__=='__main__':
    
    # on test u(t) et v(t)
    print(u(0))
    print(v(0))

    # on test solexact 
    T=1
    h=10**(-1)
    t=[k*h for k in range(0,int(T/h)+1)]
    print(f'les valeures de t sont :  {t}')
    print(f'les valeures de u et v  en t exactes sont : {solexacte(t)}')
    print(f'les valeures numeriique de u et v  en t sont : {solnum(0,0,T,h)}')