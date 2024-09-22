#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys 
from geo.point import Point 
from geo.tycat import tycat 
from geo.segment import Segment


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

def print_components_sizes( distance, pts):

    # Remplissage de notre graphe G 

    v=pts
    G={s:[] for s in  pts}
    for  pi   in v :
         for pj  in v :
             if pj.distance_to(pi) < distance and pj!=pi:
                        G[pi].append(pj)

    # representation de notre graphe avec tycat 
    rep=[v]
    for cle , valeur in G.items():
        for point in valeur :
            rep.append(Segment([cle,point]))
    
    tycat(rep)





   
def main():
    """
    ne pas modifier: on charge une instance et on affiche les tailles
    """
    for instance in sys.argv[1:]:
        distance, points = load_instance(instance)
        print_components_sizes(distance, points)

main()