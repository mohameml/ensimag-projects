# Erreurs étape B :



## 1. Règles communes aux trois passes de vérifications contextuelles :

- **Règle (0.1):**
    
    ![0.1](../images/0_1.jpeg)
    <br>
    - raison : opération partielle  env_exp(name)
    - message : « identificateur non déclaré »


- **Règle (0.2) :**

    ![0.2](../images/0_2.jpeg)
    <br>
    
    - raison : opération partielle env_types(name)
    - message : « identificateur de type non déclaré »



## 2. Grammaire attribuée spécifiant la passe 1:

- **Règle (1.3) :** 

    ![1.3](../images/1_3.jpeg)
    <br>

    - raison : opération paratielle [L’identificateur super doit être un identificateur de classe préalablement déclaré]
    - message : "identificateur non déclaré "

    - raison : condition 
    - message : " identificateur de classe attendu"

    - raison : opération union disjointe 
    - message : "classe ou type déjà déclaré "




### 3. Grammaire attribuée spécifiant la passe 2 :

- **Règle (2.3) :** 

    ![2.3](../images/2_3.jpeg)
    <br>
    
    - raison : opération partielle env_types(super)
    - message : "en fait erreur interne : on doit pas arriver ici "

    - raison : condition 
    - message : " identificateur de classe attendu"

    - raison : opération partielle union disjointe
    - message : "un nom de méthode redéclare un nom de champ "



- **Règle (2.4) :** 

    ![2.4](../images/2_4.jpeg)
    <br>

    - raison : opération partielle union disjointe .
    - message : "nom d’attribut déjà déclaré ".


- **Règle (2.5) :** 
    
    ![2.5](../images/2_5.jpeg)
    <br>

    - raison : condition type != void .
    - message : "le type attendu doit étre différent de void".

    - raison : condition et opération partielle env_types(super)
    - message : "en fait erreur interne : on doit pas arriver ici "

    - raison :si le champ est deja définir dans le super class ---> elle doit étre de type field 
    - message : "le  type attendu est field "


- **Règle (2.6) :** 

    ![2.6](../images/2_6.jpeg)
    <br>


    - raison : opération partielle union disjointe 
    - message : "méthode déja déclaré".


- **Règle (2.7) :** 

    ![2.7](../images/2_7.jpeg)
    <br>

    - raison : condition et opération partielle env_types(super)
    - message : "en fait erreur interne : on doit pas arriver ici "


    - raison : condition 
    - message : " identificateur de classe attendu"

    - Si une méthode est redéfinie alors :
        
        - raison : sig = sig2;
        - message : "une méthode redéfinie doit avoire la méme signature"

        - raison : subtype(env_types, type, type2)
        - message : "une méthode redéfinie doit avoir pour type de retour un sous-type du type de retour de la méthode héritée"

- **Règle (2.9) :** 

    ![2.9](../images/2_9.jpeg)
    <br>

    -  raison :type != void
    - message : "le type attendu doit étre différent de void"



### 4. Grammaire attribuée spécifiant la passe 3 :

- **Règle (3.5) :** 

    ![3.5](../images/3_5.jpeg)
    <br>

    - raison : opération partielle env_types(super)
    - message : "en fait erreur interne : on doit pas arriver ici "

    - raison : condition 
    - message : " identificateur de classe attendu"


- **Règle (3.12) :** 

    ![3.12](../images/3_12.jpeg)
    <br>
    - raison : opération union disjointe env_expr ⊕ env_exp
    - message : " paramètre deja déclarée "


- **Règle (3.17) :** 

    ![3.17](../images/3_17.jpeg)
    <br>

    - raison :  opération union disjointe
    - message : " variable  deja déclarée "

    - raison : condition type != void 
    - message : "le type attendu doit étre différent de void"

    
- **Règle (3.24) :** 

    ![3.24](../images/3_24.jpeg)
    <br>

    - raison : condition return != void 
    - message : " paramètre deja déclarée "

- **Règle (3.28) :** 

    ![3.28](../images/3_28.jpeg)
    <br>

    - raison : condition assign_compatible(env_types, type1, type2)
    - message : "cette affection n'est pas cohérente "

- **Règle (3.29) :** 

    ![3.29](../images/3_29.jpeg)
    <br>

    - raison : Filtrage d’un attribut synthétisé en partie droite : doit étre boolean .
    - message : "type attendu est boolean".


- **Règle (3.31) :** 

    ![3.31](../images/3_31.jpeg)
    <br>

    - raison : condition type = int ou type = float ou type = string
    - message : "le type attendu int , float , string "

- **Règle (3.39) :** 

    ![3.39](../images/3_39.jpeg)
    <br>
    - raison : condition cast_compatible(env_types, type2, type)
    - message : "casting non compatible "

- **Règle (3.42) :** 

    ![3.42](../images/3_42.jpeg)
    <br>

    - raison : condition type = type_class(__)
    - message : "le type attendu : type_class"


- **Règle (3.43) :** 
    
    ![3.43](../images/3_43.jpeg)
    <br>

    - raison : condition class!=0
    - "class non définie"


- **Règle (3.65) :** 

    ![3.65](../images/3_65.jpeg)
    <br>

    - raison : opération partielle env_types(class2)
    - message : "en fait erreur interne : on doit pas arriver ici "
    
    - raison : condition 
    - message : " identificateur de classe attendu"

    - raison : Filtrage d’un attribut synthétisé en partie droite: field_ident ↓env_exp2 ↑public ↑__ ↑type
    - message : "accès pas possible : doit étre public "

- **Règle (3.66) :**

    ![3.66](../images/3_66.jpeg)
    <br>


    - raison : opération partielle env_types(class2)
    - message : "en fait erreur interne : on doit pas arriver ici "
    
    - raison : condition 
    - message : " identificateur de classe attendu"

    - raison : Filtrage d’un attribut synthétisé en partie droite: field_ident ↓env_exp2 ↑protected ↑class_field ↑type
    - message : "visibilité attendu : protected "

    - raison : subtype(env_types,type_class(class2),type_class(class))
    - message : "le type de class2 doit étre un sous type de classe"


    - raison : subtype(env_types,type_class(class),type_class(class_field))
    - message : "le type de class doit étre un sous de type de class field "



- **Règle (3.67) , (3.68) , (3.69) :**

    ![3.67](../images/3_67.jpeg)
    <br>
    - raison : Filtrage d’un attribut synthétisé en partie droite
    - message :"nature attendu : champ , paramètre , varibale  


- **Règle (3.70) :**

    ![3.70](../images/3_70.jpeg)
    <br>
    - raison : Filtrage d’un attribut synthétisé en partie droite
    - message : "type attendu : field "

- **Règle (3.71) :**

    ![3.71](../images/3_71.jpeg)
    <br>
    - raison : opération partielle env_types(class2)
    - message : "en fait erreur interne : on doit pas arriver ici "
    
    - raison : condition 
    - message : " identificateur de classe attendu"

- **Règle (3.72) :**

    ![3.72](../images/3_72.jpeg)
    <br>

    - raison : Filtrage d’un attribut synthétisé en partie droite
    - message : "type attendu : méthode "

- **Règle (3.73) :**

    ![3.73](../images/3_73.jpeg)
    <br>
    - raison : Filtrage d’un attribut hérité en partie gauche
    - message : "signature attendu : []"
