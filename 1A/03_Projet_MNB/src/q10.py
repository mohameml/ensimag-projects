#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np
from q8 import  solnum
from q9 import solexacte

# question 10 
#on fixe les constatntes
T=25
u0=0
v0=0
def Err(h):
    
    kmax=int(T/h)
    mat_num=solnum(u0,v0,T,h)
    mat_exacte=solexacte([k*h for k in range(0,kmax+1)])
    err=[]
    
    for k in range(0, kmax+1):
        valeur=(mat_num[0][k]-mat_exacte[0][k])**2 + (mat_num[1][k]-mat_exacte[1][k])**2
        err.append(np.sqrt(valeur))
    return max(err)

# Tracage de la fonction Err(h) en fonction de h
pas=(10**(-1)-10**(-5))/101
val_h=[10**(-5)+k*pas for k in range(0,101)]
val_err=[Err(h) for h in val_h]
plt.plot(val_h,val_err,label="Err(h)")
val_h_2 =[h**2 for h in val_h]
plt.plot(val_h,val_h_2,label="h^2")
val_h_3 =[h**3 for h in val_h]
plt.plot(val_h,val_h_3,label="h^3")
plt.xlim(10**(-1),10**(-5))
plt.legend()
plt.show()


print("D'apres l'affichage on que l'ordre de consistance est 2 ")

