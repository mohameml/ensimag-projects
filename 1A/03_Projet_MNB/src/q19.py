#!/usr/bin/env python3 
import matplotlib.pyplot as plt
import numpy as np


from q16 import K ,fvec
from q17 import factorise

 
# question 19 
# on definit les constantes 
h=10**(-3)
n=5
mi=0.5
T=25 # par exmple 
# on definit la matrice C 


#on definit la matrrice A
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




# on definit des fonction de tracage de graphe 
def tracage_u(mat,j):
    val_x=np.linspace(0,T,int(T/h)+1)
    val_y=[mat[i][j] for i in range(int(T/h)+1)] # val_y représente : L'ensembles des uapp(j,t)
    plt.plot(val_x,val_y)
    plt.xlim(0,T)
    plt.title(f"représentation de uapp({j},t) ")
    plt.legend()
    plt.show()

def mat_derive_u(mat_u,mat_v):
    "return l'approximation de la derivée de u aux pts tk "
    # on a u'=v-Cu
    size=np.shape(mat_u)
    val_derive_u=np.zeros(size)
    for i in range(n):
        val_derive_u[i]=mat_v[i]-np.dot(C,mat_u[i])
    return val_derive_u
    

def tracage_derive_u(mat_u,mat_v,j):
    
    derive_u=mat_derive_u(mat_u,mat_v)
    val_x=np.linspace(0,T,int(T/h)+1)
    val_y=[derive_u[i][j] for i in range(int(T/h)+1)]
    plt.plot(val_x,val_y)
    plt.xlim(-T/1000,T/1000)
    plt.ylim(-T/100000000000000000,T/100000000000000000)
    plt.title(f"représentation de l'approximation de la derive de u({j},t) ")
    plt.legend()
    plt.show()



if __name__=="__main__":
    


    u0=np.zeros(n)
    v0=np.zeros(n)
    matrice_u,matrice_v=solchaine(u0,v0,T,h)
    mat_d=mat_derive_u(matrice_u,matrice_v)

        
    # Réprsentation de u
    tracage_u(matrice_u,0) # représentation ds oscialltions au début 
    tracage_u(matrice_u,2) # représentation ds oscialltions au milieu  
    tracage_u(matrice_u,4) # représentation ds oscialltions et au fin 

        
    # Réprsentation de la derivée de u  
    tracage_derive_u(matrice_u,matrice_v,0) # représentation ds oscialltions au début 
    tracage_derive_u(matrice_u,matrice_v,2) # représentation ds oscialltions au milieu  
    tracage_derive_u(matrice_u,matrice_v,4) # représentation ds oscialltions et au fin 


    # L'ordre de grandeur du temps  teq,  nécessaire pour atteindre un état proche de l'équilibre
    "a complenter"
