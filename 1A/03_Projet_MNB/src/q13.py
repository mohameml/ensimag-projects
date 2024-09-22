#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np


# On implementé f(u) tout d'abord 
def f(u):
    return 1-u**3

def tracage_C(c):
    
    
        # on definit les constantes :
        T=30
        h=10**(-3)
        
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



            
            
        t=[k*h for k in range(0,int(T/h)+1)]
        mat_num =solnum(0,0,T,h)
        
            

        val_u_k = mat_num[0]
        val_v_k = mat_num[1]
        plt.title(f"pour c={c}")
        plt.plot(t,val_u_k,label="u_k")
        plt.plot(t,val_v_k,label="v_k")
            
        plt.legend()
        plt.show()
    






    
# tracge 
tracage_C(0.3)
tracage_C(0.1)
tracage_C(2)
tracage_C(1)

print("Donc on remarque que pour c <= 1 on a la converge est oscillante (régime sous-amorti),") 
print(" et pour c > 1 on a la convergence vers l'équilibre s'effectue de manière monotone (régime sur-amorti")     