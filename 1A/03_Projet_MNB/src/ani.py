#!/usr/bin/env python3


import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
from q19 import tracage_u
from q18 import solchaine
T=2500
n=100
mi=5
h=10**(-2)




u0=np.zeros(n)
v0=np.zeros(n)
matrice_u,matrice_v=solchaine(u0,v0,T,h)




#inisialisation de la figure
fig=plt.figure()
line,=plt.plot([],[]) # le "," dans la defintion de line est imporatnt si non python considere line une liste et non un graphe 
plt.xlim(0,T*2)
plt.ylim(0,T/1000)


# la fonction tracage : pour tracer pluusieres graphes pour l'animation
A=matrice_u
j=1
def tracage(h):
    x=np.linspace(0,T,int(T/h)+1)
    y=[A[i][j] for i in range(int(T/h)+1)]
    line.set_data(x,y)
    return line,

#Animation  
""" 
 FuncAnimation : est une fonction qui prend en parametres :
      un fig    
      la fonction de tracage
      frames=n avec nombres de .....
      interval =m avec m le temps en ms entre deux applles de la fonction "tracag"
       


"""
anim=animation.FuncAnimation(fig,tracage,frames=[k*h for k in range(1,100000)],interval=100,blit=True,repeat=False)


plt.show()

