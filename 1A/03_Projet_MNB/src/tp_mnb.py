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
    uk_plus_1=(1/(1+(h/2)*c))*((1-(h/2)*c)*uk +h*inter)
    
    # on calcul v(k+1)
    b=f(uk_plus_1)
    vk_plus_1=inter+(h/2)*b
    
    # on return u(k+1),v(k+1)
    return [uk_plus_1,vk_plus_1]

# On test la fonction iter sur(u*,v*)
u=1 # u represente u*
v=c # v represente v*
h=0.1
print(f'la valeur de iter en (u*,v*) est, {iter(u,v,h)}' )



# question 8
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
#test de solnum
c=0.3
T=1
h=10**(-1)
matrice=solnum(u,v,T,h)
print(f'l\'approximation de u,v aux pointx tk=kh avec les conditions initiales (u*,v*) est {matrice}  ' )



# question 9 
c=0.3
r=-c/2
s=np.sqrt(4-c**2)/2
b=-c/np.sqrt(4-c**2)

def u(t):
    return np.exp(r*t)*(-np.cos(s*t)+b*np.sin(s*t))+1

def v(t):
    
    a=1/(r**2+s**2)
    p1=r*(np.exp(r*t)*np.cos(st)-1)+np.sin(s*t)*s*np.exp(r*t)
    p2=-s*np.exp(r*t)*np.cos(s*t)+r*np.sin(s*t)*np.exp(r*t)+s
    
    return -t+a*p1-b*a*p2
    
def solexacte(t):
    
    kmax=len(t)-1
    matrice=np.zeros(2,kmax+1)
    
    for i in range(1,kmax+1):
        a=u(t)
        b=v(t)
        matrice[0][i]=a
        matrice[1][i]=b
        
    return matrice


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
    return err

# Tracage de la fonction Err(h) en fonction de h
pas=(10**(-1)-10**(-5))/101
val_h=[10**(-5)+k*pas for k in range(0,101)]
val_err=[Err(h) for h in val_h]
plt.plot(val_h,val_err)
plt.xlim(10**(-1),10**(-5))
plt.show()

# question 16 
# la fonction fvec(u) return f(u)=-Ku³+e1
def fvec(u,n):
    "return f(u)"
    # on calcul K tout d'abord 
    k=np.zeros((n,n))
    for i in range(n):
        k[i][i]=2
        k[i][i-1],k[i][i+1]=-1,-1
        
    #on calcule 
    u_3=(u*u)*u
    b=np.dot(K,u_3)
    
    #on definit e1
    e1=zeros(n)
    e1[0]=1
    
    # on return f(u)
    
    return (-1)*b+e1

# Test de la fonction fvec sur u*



# question 17

 """
on implement l'Algo de la factorisation de chloskey car 
A est symétrique definie positive
 et apres T=LD*(1/2)
"""
def factorise(n,mi,h):
    # on definit A tout d'abord 
    " A=I+h/2C avec C = miK "
    I=np.ones((n,n))
    C=mi*K
    A=I+(h/2)*K
    
    # on calcul tout d'abord T ta A=TT^t 
    T=np.zeros((n,n)) 
    for j in range(1,n+1):
        T[j][j]=(A[j][j])-(sum([(T[j][k])**2 for k in range(1,j)])**(1/2))
        for i in range(j+1,n+1):
            T[i][j]=(1/T[j][j])*(A[i][j]-sum([T[i][k]*T[j][k] for k in range(1,j)]))
                           
    # on calcul D :D[i][i]=(T[i][i])**2
    D=np.zeros(n)
    for i in range(1,n+1):
        D[i]=(T[i][i])**2
    
    # on calcul L on a L[i][i-1]=T[i][i-1]/((D[i-1][i-1])**(1/2))
    L=np.zeros(n-1)
    for i in range(1,n):
        L[i-1]=T[i][i-1]/((D[i-1][i-1])**(1/2))
                           
    # on return les deux vecteures L et D :
    return L,D


# quesion 18 
# on fixe des valeures de n ,mi , T et h 
n=3 # la dimension 
mi=1
T=20
h=10**(-4)
# on definit notre  matrice K 
k=np.zeros((n,n))
for i in range(n):
    k[i][i]=2
    k[i][i-1],k[i][i+1]=-1,-1

#  on definit notre matrrice A
" A=I+h/2C avec C = miK "
I=np.ones((n,n))
C=mi*K
A=I+(h/2)*K

def resoudre(A,b):
    "prend en parametres : "
    "return la solution de Ax=b"
    D,L=factorise(n,mi,h)
    # on resoudre Ly=b "descente"
    sol_des=np.zeros(n) # sol_des reprenste y 
    for k in range(n):
        sol_des[k]=b[k]-l[k-1]*sol_des[k-1]
    
    # on resoudre Ux=y "remontéé"
    "on calcul U=D*trans(L)"
    # on recupere L et apres on calcul transp(L)
    L_u=np.zeros((n,n))
    for i in range(0,n):
        L_u[i][i-1]=L[i]
    L_u_t=np.transpose(L_u)
    # on recupere la matrice D_u partir du vteur D:
    D_u=np.zeros((n,n))
    for i in range(n):
        D_u[i][i]=D[i]
    # on calcul U
    U=np.dot(D_u,L_u_t)
    
    # on calclu x la soltuoin de Ux=y
    sol=np.zeros(n)
    for k in range(n,0,-1):
        sol[k]=(1/U[k][k])*(sol_des[k]-sum([U[k][j]*sol[j] for j in range(k+1,n)]))
        
    return sol



def solchaine(u0,v0,T,h):
    "return deux maatrices de taille n*(kmax+1)"
    
    # on definit les constantes 
    kmax=int(T/h)
    tmeps=[k*h for k in range(0,kmax+1)]
    # on inisialise le matricve qui return les valeures de u(k*h)
    matrice_u=np.zeros((n,1+kmax))
    matrice_u[0]=u0
    #on inisialise le matricve qui return les valeures de v(k*h)
    matrice_v=np.zeros((n,1+kmax))
    matrice_v[0]=v0
    
    # on ramplir mnt les deux matrcices 
    for k in range(1,kmax+1):
        #on calcul mnt v(k+1/2)
        inter=matrice_v[k-1]+(h/2)*fvec(matrice_u[k-1],n)
        
        # on resoudre l'equation Ax=b avec x=u(k+1) avec la fonction resoudre 
        b=np.dot((I-(h/2)*C),matrice_u[k-1]) +h*inter
        matrice_u[k]=resoudre(A,b)
        
        # on calcul v(k+1)
        matrice_v[k]=inter+(h/2)*fvec(matrice_u[k],n)
        
        
        # on return les deux matrices : matrice_u et matrice_v
        return  matrice_u,matrice_v

# on Test mnt la fonction solchaine avec [u*,v*] sont les condtions initiales
u0 = 0
solchaine(u0,v0,T,h)


# question 19 
# on definit les constantes 
h=10**(-3)
n=5
mi=0.5
T=25 # par exmple 
# on definit la matrice C 
k=np.zeros((n,n))
for i in range(n):
    k[i][i]=2
    k[i][i-1],k[i][i+1]=-1,-1
C=mi*k

# calcul de la solution numerique de (1)-(6)
u0=np.zeros(n)
v0=np.zeros(n)
matrice_u,matrive_v=solchaine(u0,v0,T,h)

# on definit des fonction de tracage de graphe 
def tracage_u(mat,j):
    val_x=np.linspace(0,T,int(T/h))
    val_y=[mat[i][j] for i in range(n)] # val_y représente : L'ensembles des uapp(j,t)
    plt.plot(val_x,val_y)
    plt.xlim(0,T)
    plt.title(f"représentation de uapp({j},t) ")
    

def derive_u(mat_u,mat_v):
    "return l'approximation de la derivée de u aux pts tk "
    # on a u'=v-Cu
    size=np.shape(mat_u)
    val_derive_u=np.zeros(size)
    for i in range(n):
        val_derive_u[i]=mat_v[i]-np.dot(mat_u[i],C)
    return derive_u
    

def tracage_derive_u(mat_u,mat_v,j,j):
    derive_u=derive_u(mat_u,mat_v)
    val_x=np.linspace(0,T,int(T/h))
    val_y=[derive_u[i][j] for i in range(n)]
    plt.plot(val_x,val_y)
    plt.xlim(0,T)
    plt.title(f"représentation de l'approximation de la derive de u({j},t) ")
    
# Réprsentation de u
tracage_u(matrice_u,1) # représentation ds oscialltions au début 
tracage_u(matrice_u,3) # représentation ds oscialltions au milieu  
tracage_u(matrice_u,5) # représentation ds oscialltions et au fin 

    
# Réprsentation de la derivée de u  
tracage_derive_u(matrice_u,matrice_v,1) # représentation ds oscialltions au début 
tracage_derive_u(matrice_u,matrive_v,3) # représentation ds oscialltions au milieu  
tracage_derive_u(matrice_u,matrive_v,5) # représentation ds oscialltions et au fin 


# L'ordre de grandeur du temps  teq,  nécessaire pour atteindre un état proche de l'équilibre
"a complenter"

# questtion 20 
#inisialisation de la figure
fig=plt.figure()
line,=plt.plot([],[]) 
plt.xlim(0,T)
# ajoue de graphe a line 
line.set_data(x,np.sin(x))

#Animation  

anim=animation.FuncAnimation(fig,tracage_u,frames=100,interval=100,blit=True,repeat=False)


plt.show()