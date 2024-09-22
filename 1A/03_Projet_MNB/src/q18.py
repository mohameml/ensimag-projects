#!/usr/bin/env python3 
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np

from q16 import K , fvec
from q17 import  factorise 



# quesion 18 
# on fixe des valeures de n ,mi , T et h 
n=100 # la dimension 
mi=5
T=25
h=10**(-2)


#  on definit notre matrrice A
" A=I+h/2C avec C = miK "
I=np.identity(n)
C=mi*K(n)
A=I+(h/2)*C

def resoudre(n,mi,h,b):
    "prend en parametres :  n,mi,h et un veteur b "
    "return la solution de Ax=b"
    
    L,D=factorise(n,mi,h)
    # on resoudre Ly=b "descente"
    sol_des=np.zeros(n) # sol_des reprenste y 
    sol_des[0]=b[0]
    for k in range(1,n):
        sol_des[k]=b[k]-L[k-1]*sol_des[k-1]
    
    # on resoudre Ux=y "remontéé"
    "on calcul U=D*trans(L)"
    # on recupere L et apres on calcul transp(L)
    L_u=np.zeros((n,n))
    L_u[0][0]=1
    for i in range(1,n):
        L_u[i][i]=1
        L_u[i][i-1]=L[i-1]
    L_u_t=np.transpose(L_u)
    # on recupere la matrice D_u partir du vteur D:
    D_u=np.diag(D)
    # on calcul U
    U=np.dot(D_u,L_u_t)
    
    # on calclu x la soltuoin de Ux=y
    
    sol=np.zeros(n)
    for k in range(n-1,-1,-1):
        sol[k]=(1/U[k][k])*(sol_des[k]-sum([U[k][j]*sol[j] for j in range(k,n)]))
        
    return sol



def solchaine(u0,v0,T,h):
    "return deux maatrices de taille n*(kmax+1)"
    
    # on definit les constantes 
    kmax=int(T/h)
    
    # on inisialise le matrice qui return les valeures de u(k*h)
    matrice_u=np.zeros((kmax+1,n))
    matrice_u[0]=u0
    #on inisialise le matricve qui return les valeures de v(k*h)
    matrice_v=np.zeros((kmax+1,n))
    matrice_v[0]=v0
    
    # on ramplir mnt les deux matrcices 
    for k in range(1,kmax+1):
        #on calcul mnt v(k+1/2)
        inter=matrice_v[k-1]+(h/2)*fvec(matrice_u[k-1],n)
        
        # on resoudre l'equation Ax=b avec x=u(k+1) avec la fonction resoudre 
        b=np.dot((I-(h/2)*C),matrice_u[k-1]) +h*inter
        matrice_u[k]=resoudre(n,mi,h,b)
        
        # on calcul v(k+1)
        matrice_v[k]=inter+(h/2)*fvec(matrice_u[k],n)
        
        
    
    # on return les deux matrices : matrice_u et matrice_v
    return  matrice_u,matrice_v
