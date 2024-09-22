#!/usr/bin/env python3 
import matplotlib.pyplot as plt
import numpy as np


# on definit la matrice C 


def mat_derive_u(mat_u,mat_v):
    "return l'approximation de la derivée de u aux pts tk "
    # on a u'=v-Cu
    size=np.shape(mat_u)
    val_derive_u=np.zeros(size)
    for i in range(3):
        val_derive_u[i]=mat_v[i]-np.dot(mat_u[i],C)
    return val_derive_u
    
C=np.array([[1,2,3],[0,1,1],[2,1,0]])
mat_u=np.array([[1,2,3],[0,1,1],[2,1,0]])
mat_v=np.array([[1,1,3],[0,1,1],[2,1,0]])
print(mat_derive_u(mat_u,mat_v))


