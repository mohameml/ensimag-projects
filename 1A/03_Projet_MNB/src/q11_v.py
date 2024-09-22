#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np

from q8 import  solnum
from q9 import  solexacte






def tracage_v_k(T,h):
    
    
    t=[k*h for k in range(0,int(T/h)+1)]
    mat_num =solnum(0,0,T,h)
    mat_excate =solexacte(t)
    

    val_v_k = mat_num[1]
    val_v_t= mat_excate[1]
    plt.plot(t,val_v_k,label="u_k")
    plt.plot(t,val_v_t,label="u(t)")
    
    plt.legend()
    plt.show()

# pour h=10**(-1) et T=25
T=25
h=1
tracage_v_k(T,h)
    

# pour h=10**(-2) et T=25
T=25
h=0.5
tracage_v_k(T,h)

# pour h=10**(-3) et T=25
T=25
h=10**(-1)
tracage_v_k(T,h)


print("Donc pour T=25 on' a  la convergance de v_k vers v(t) a partir de h=10^{-1}")