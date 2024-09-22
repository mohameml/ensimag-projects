#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np


# question 7 : 
#valeur de c pour le test 
c=0.3

# On implementé f(u) tout d'abord 
def f(u):
    return 1-u

# on implemente iter(uk,vk,h)
def iter(uk,vk,h):
    
    # on calcul v(k+1/2)
    a=f(uk)
    inter=vk+(h/2)*a # inter c'est v(k+1/2)
    
    #on calcul u(k+1)
    A=1+(h/2)*c
    uk_plus_1=(1/A)*((1-(h/2)*c)*uk +h*inter)
    
    # on calcul v(k+1)
    b=f(uk_plus_1)
    vk_plus_1=inter+(h/2)*b
    
    # on return u(k+1),v(k+1)
    return [uk_plus_1,vk_plus_1]

# On test la fonction iter sur(u*,v*)



if __name__=="__main__":
    
    u=1 # u represente u*
    v=c # v represente v*
    h=0.1
    print(f'la valeur de iter en (u*,v*) est, {iter(u,v,h)}' )