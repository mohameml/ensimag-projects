#!/usr/bin/env python3
"""
calculer les tailles de tous les composants connectés.
trier et afficher.
"""

from timeit import timeit
# from sys import argv

from geo.point import Point

from geo.segment import Segment
from geo.tycat import tycat 
import time 
import random 
import matplotlib.pyplot as plt 
import numpy as np



def load_instance(filename):
    """
    charge le fichier .pts.
     renvoie la limite de distance et les points.
    """
    with open(filename, "r") as instance_file:
        lines = iter(instance_file)
        distance = float(next(lines))
        points = [Point([float(f) for f in l.split(",")]) for l in lines]

    return distance, points





def comm_connexe(sommet,points,distance):
    
    col={s:"blanc" for s in points}
    col[sommet]="gris"
    pile=[sommet]
    
    
    while pile :
        
        # on prend la  tete de la pile
        u=pile[-1]
        # on cherche les enfants non parcouris 
        R=[y for y in points if col[y]=="blanc"  and  u.distance_to(y) < distance ]
        
        if R :
             v=R[0] # on prend le premiere sommet de R 
             col[v]="gris" # on le marque comme visite 
             pile.append(v) # on l'empile v dans la pile 
        
        else :
            pile.pop()

            
    return  [s for s in points if col[s]=="gris"]      
    


def print_components_sizes( distance, vocabulaire):
    
    " affichage des tailles triees de chaque composante "

    
    # Les composantes connexes :
    tailles=[]

    
    col={v:"blanc" for v in vocabulaire}
    for point in vocabulaire :

        if col[point]!="noir" :
            com=comm_connexe(point,vocabulaire,distance)
            tailles.append(len(com))
          
        for s in com :
            col[s]="noir"

    # affichage final
    tailles.sort(reverse=True)
    # print(tailles)
                
                    

def fichier_aléatoire(k):
    fich=open(f"exemples_{k}.pts","w")
    print(f"{k/50000}",file=fich)
    for _ in range(k) :
        n1=random.random()/10
        n2=random.random()/10
        print(f'{n1},',n2,file=fich)
        
    fich.close()




            

def main(fichier):
    """
    ne pas modifier: on charge une instance et on affiche les tailles
    """
    # for instance in argv[1:]:
    distance, points = load_instance(fichier)
    print_components_sizes(distance, points)



nb_points=[k for k in range(1000,4000,100)]
temps=[]

for i in nb_points :

    fichier_aléatoire(i)
    fich=f"exemples_{i}.pts"
    t1=time.time()
    main(fich)
    t2=time.time()
    temps.append(t2-t1)
    

y_nlogn=[n*np.log(n) for n in nb_points]
y_n_2=[n**2 for n in nb_points]
y_n_3=[n**3 for n in nb_points]

plt.plot(nb_points,temps,label="temps d'exc")

plt.legend()
plt.show()
    






# print(f"le temps d'excution est : {t2-t1}")




