# etape B :

## 1. Principaux répertoires concernés :cf. I-[Consignes]


- **``src/main/java/fr/ensimag/deca/tree/ :``**
    - Classes qui définissent les arbres
    - Méthodes de parcours de l’arbre abstrait

- **``src/main/java/fr/ensimag/deca/context/ :``**
    - Fichiers sources Java concernant l’étape B

- **``src/test/java/fr/ensimag/deca/context/:``**
    - Fichiers Java de test concernant l’étape B.

- **``src/test/deca/context/:``**
    - src/test/deca/context/
 

## 2. Classes fournies :cf. I-[Consignes]

- **Classes pour les types :**
    - Classe abstraite ``Type``, dont dérivent les classes ``StringType``, ``VoidType``, ``BooleanType``, ``IntType``, ``FloatType``, ``NullType`` et ``ClassType``.


- **Classes pour les définitions :**

    - Classe abstraite ``Definition`` ;

    - Classes ``VariableDefinition``, ``ParamDefinition``, ``ClassDefinition``, ``FieldDefinition``, ``MethodDefinition``...
 
- **Classe Signature :** (pour la signature des méthodes)

- **Classe EnvironmentExp:** (squelette fourni, à implémenter)

- Exception **ContextualError**, levée lorsqu’on détecte une erreur contextuelle (on s’arrête à la première erreur  contextuelle).


## 3. Parcours de l’arbre abstrait:


- Trois parcours à effectuer.

- Parcours basés sur la syntaxe abstraite du langage II-[SyntaxeAbstraite]

- Une méthode abstraite ``verifyXyz`` pour chaque non terminal ``XYZ`` (ou classe ``AbstractXyz``) de la grammaire d’arbres :

    - Dans la classe ``AbstractProgram`` :
        - **``abstract void verifyProgram ( DecacCompiler compiler ) throws ContextualError ;``**
    
    - Dans la classe ``AbstractMain :``

        - **``abstract void verifyMain ( DecacCompiler compiler ) throws ContextualError ;``**

- Ces méthodes abstraites sont ensuite implémentées dans les sous-classes (``Program``, ``EmptyMain``, ``Main``...)


- **Codage des attributs de la grammaire attribuée :**
    - paramètres pour les attributs hérités ;
    - résultat de méthode pour un attribut synthétisé.

    - Exceptions :
        
        - **env_exp :** en général, dans la grammaire, on trouve un attribut hérité (pour la valeur « avant ») et un attribut synthétisé (pour la valeur « après » application de la règle). Dans l’implémentation, on utilise un seul objet de type EnvironmentExp, que l’on mute.

        - **env_types :** on peut stocker un environnement dans les objets de type DecacCompiler, de cette façon il n’est pas nécessaire de le passer en paramètres dans la plupart des méthodes.

        - On peut avoir besoin de paramètres supplémentaires pour les décorations.




## 4. Travail à effectuer pour l’étape B :

- **Compléter le code des méthodes :**
    - boolean sameType(Type otherType) ;
    - boolean isSubClassOf(ClassType potentialSuperClass) ;

- implémenter la  **Classe EnvironmentExp** .

- implémenter Classe de test de la classe EnvironmentExp.

- **Implantation des trois parcours de l’arbre:**
    
    -  Implantation des méthodes **``verifyXyz``** dans les classes Java qui définissent l’arbre abstrait

    - Décoration et enrichissement de l’arbre.

- On fournit le script ``test_context``, qui appelle la classe ``ManualTestContext`` qui permet de tester l’analyse contextuelle


## 5. Méthode de travaille :

- **Analyse :**
    - comprend bien les fonctions à implémenter (texte brut) 

- **conception :**
    - faire le schéma UML du code fourin (l'outil plantUML)

- **implémentation :**
    - on écrire du code java 

- validation :
    - on écrit le test (java , deca , script )

- **documentation :** 


