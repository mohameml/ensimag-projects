# Projet Génie Logiciel, Ensimag.(gl10, 01/01/2024)

## 1. **Objectifs:**

- Écrire en Java un compilateur pour le langage ``Deca`` (petit langage ressemblant à Java) : 

    - Langage de programmation du compilateur : **``Java``**
    
    - Langage source : **``Deca``**

    - Langage cible : **``langage d’assemblage``**  pour une « machine abstraite ».


- Adopter une démarche qualité, avec un objectif de produire du code « zéro défaut », en développant une base de tests capable d’évaluer la conformité de ce compilateur à la spécification fournie dans la doc.

- Mettre en place une organisation projet et une démarche de développement.

-  Utiliser des outils d’aide au développement : **``Maven``** , **``ANTLR``**, **``Git``**, **``Jacoco``**, un  **``IDE``** (NetBeans, Eclipse, . . . ).

- Comprendre la façon dont les calculs sont traduits par les machines, par exemple sur les flottants.

- Expérimenter des techniques agiles de développement : développement dirigé par les tests, intégration continue, programmation par paires, etc.

- Comprendre et respecter des spécifications, formelles (grammaires attribuées) ou non.



## 2. langage Deca :

- **Introduction:**

    - Deca est un sous-ensemble de Java, avec quelques variations.

    - On peut déclarer :
        - des classes,  
        - des champs,
        - des méthodes

    - un programme principal.

- **Sous-langages de Deca:**

    - **langage Hello-world:** affichage des chaînes de caractères ;
        ```deca
        {println("Hello world !");}
        ```

    - **langage sans-objet:** on ne peut pas déclarer de classes, donc pas d’attributs et pas de méthodes. On ne peut donc avoir qu’un programme principal ;

    - **langage essentiel:** sauf les conversions (cast) et les tests d’appartenance à une classe (instanceof) ;

    - **langage complet tout:** !

- **Fichiers inclus:**

    - On peut inclure des fichiers grâce à la construction : `#include "fich.decah".`

- **Bibliothèque standard:**
    
    - Recherche d’un fichier inclus :
        - dans le répertoire courant,
        - puis dans la bibliothèque standard (par ex. pour la classe Math), emplacement **``src/resources/include/``** de la hiérarchie imposée.
    
    - Code Deca sous forme de fichiers à inclure **``.decah``**
    


## 3. compilateur :

Le compilateur comporte trois étapes :
    
- **étape A :une seule passe**
        
    - **analyse lexicale:** L’analyse lexicale consiste à reconnaître les « mots ».

    - **analyse syntaxique:** L’analyse syntaxique consiste à vérifier que la suite de mots est une
        « phrase » correcte du langage.

    - **construction de l’arbre abstrait:** En même temps qu’on effectue l’analyse syntaxique, on construit
        l’« arbre abstrait » du programme source Deca (représentation structurée du programme sous la forme d’un arbre).

- **étape B :3 parcours de l’arbre abstrait**

    - **vérifications contextuelles.:** La syntaxe contextuelle de Deca (cf. [SyntaxeContextuelle]) définit les
        programmes Deca contextuellement correct (Grammaire attribuée de Deca) .

    - **décoration de l’arbre abstrait:** Pendant un parcours, on décore
        - les identificateurs avec leur « définition »,
        - les expressions avec leur « type ».

    - Trois parcours de l’arbre abstrait :
        - première passe : vérifier le nom des classes,
        - deuxième passe : vérifier les champs et signatures des méthodes,
        - troisième passe : vérifier le corps des méthodes.
 

- **étape C :**

    - **génération de code: deux passes** 
        
        - On génère du code assembleur pour une « machine abstraite », proche du 68000
        - On peut exécuter ce code grâce à un « interprète de machine  abstraite » (IMA)
        - L’étape C s’effectue en deux passes : deux parcours de l’arbre abstrait             
            -  première passe : construire la table des méthodes des classes ;
            -  deuxième passe : coder le programme.


## 4. **Développement durable:**


- **Analyse énergétique de votre projet:**

    - Efficacité du code produit :
        
        - Un compilateur est potentiellement utilisé pour produire de nombreux logiciels

        - Produire du code optimisé

    - Efficacité du procédé de fabrication de votre compilateur:
        
        - Coût des compilations
        
        - Coût de la validation (exécution des tests)

- **Pistes possibles d’analyse:**

    - Informations générales sur la consommation des ordinateurs
    - Consommation de votre propre projet (par ex. /usr/bin/times)
    - Pour l’exécution des programmes générés : considérer le nombre de cycles indiqué par la machine abstraite ima.

## 5. Planification du projet :

- **Découper le projet en tâches à réaliser :**
    - Hello-world, sans objet, essentiel
    - Extension
    - Etapes A,B,C
    - Analyse, conception, implémentation, validation <br>
    ⇒ Diagramme de tâches

- **Prendre en compte :**
    
    - Liens d’antériorité
    - Parallélisme <br>
    ⇒ Diagramme de PERT

- **Planifier les tâches:** <br>
⇒ Planning de Gantt (Outil planner)

- **A faire :**
    - **planning prévisionnel.**
    - **planning effectif.**
    - **charte d’équipe**

## 6. Documentation à rendre :

1. **Documentation utilisateur:**

    - environ 12 pages.
    - Description du compilateur du point de vue de l’utilisateur
    - Commandes et options
    - Messages d’erreurs
    - Limitations
    - Utilisation de l’extension

2. **Bilan :**

    - Bilan collectif sur la gestion d’équipe et de projet.

3. **Documentation de conception:** 
    - environ 10 à 15 pages.
    - La conception architecturale des étapes B et C
    - Les algorithmes et structures de données spécifiques

4. **Documentation de validation:[Tests]**
    - environ 10 à 15 pages.
    - Descriptif des tests.
    - Scripts de tests.
    -  Gestion des risques et gestion des rendus. [cette année est optionnelle]
    - Couverture des tests (résultats de Jacoco)
    - Méthodes de validation autres que le test

5. **Documentation de l’extension:** 
    - 20 à 30 pages.
    - Analyse bibliographique.
    -  Analyse et conception
    - Choix d’algorithmes
    - Méthode de validation
   - Validation de l’implémentation
 
6. **Documentation sur les impacts énergétiques du projet et de ses retombées:**

    - 4 à 10 pages.
    -  Moyens mis en œuvre pour évaluer la consommation énergétique de votre projet
    -  Discussion sur vos choix de génération de code
    -  Discussion sur vos choix de processus de validation
    - Prise en compte de l’impact énergétique de votre extension


## 7. **Environnement de développement:**

### 7.1 **Maven:**

- Maven est un outil qui permet de construire un logiciel Java à partir de ses sources.

- Utilisation d’un Project Object Model (POM), qui permet de décrire un projet logiciel, ses dépendances avec des modules externes et l’ordre à suivre pour sa construction (Fichier **``Projet_GL/pom.xml``**).


- **Compilation:**
    
    - ``mvn compile`` (dans le répertoire Projet_GL)


- **Exécution du compilateur sur un fichier Deca:**
    
    - ``./src/main/bin/decac test/deca/.../fichier.deca``

- **Compilation et exécution des tests:**

    - ``mvn test-compile`` (dans le répertoire Projet_GL)

    - ``mvn verify`` (dans le répertoire Projet_GL)

- **Nettoyage:**
    
    - ``mvn clean`` :efface les fichiers générés
 
- **Génération de rapports (FindBugs, PMD, Jacoco...) et documentation (JavaDoc):**
    
    - ``mvn site``
    - ``firefox target/site/index.html``