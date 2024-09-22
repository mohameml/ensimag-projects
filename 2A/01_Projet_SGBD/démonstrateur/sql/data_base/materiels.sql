---------------------------------------------------   MATERIEL : ---------------------------------------------------------------  

CREATE TABLE  Lot
(
    marque VARCHAR(50),
    modele VARCHAR(50) ,
    anneeAchat DATE  ,
    nbPieces INT , 
    Text VARCHAR(100) ,
    prixPerte INT , 
    anneePeremption INT , 
    sousCategorie VARCHAR(50) , 
    
    PRIMARY KEY(marque , modele , anneeAchat) , 
    FOREIGN KEY(sousCategorie) REFERENCES Arbre(Categorie) 
    
);




-- Arbre : 


CREATE  TABLE Arbre
(
    Categorie VARCHAR(50)  , 
    
    PRIMARY KEY(Categorie)
);


SELECT * FROM Arbre ;

-- TABLE ESTPARENTDE :

DROP TABLE EstParentDe ; 

CREATE TABLE EstParentDe 
(
    sousCategorie1 VARCHAR(50) ,
    sousCategorie2 VARCHAR(50) ,
    PRIMARY KEY(sousCategorie1 , sousCategorie2) ,
    
    FOREIGN KEY(sousCategorie1) REFERENCES Arbre(Categorie), 
    FOREIGN KEY(sousCategorie2) REFERENCES Arbre(Categorie)

);



-- COMPATIBLE_AVEC_ACTIVITE :

CREATE TABLE COMPATIBLE_AVEC_ACTIVITE 
(
    MARQUE VARCHAR(50),
    MODELE VARCHAR(50),
    ANNEeACHAT DATE ,
    NOmACTIVITE VARCHAR(50),
    FOREIGN KEY (NOmACTIVITE) REFERENCES ACTIVITE(NOmACTIVITE),
    FOREIGN KEY (MARQUE, MODELE, ANNEeACHAT) REFERENCES LOT(MARQUE, MODELE, ANNEeACHAT),
    
    PRIMARY KEY (MARQUE, MODELE, ANNEeACHAT, NOmACTIVITE)
);




-- LOCATION_MATERIEL : 

CREATE TABLE LOCATION_MATERIEL 
(
   IdLoc INT PRIMARY KEY,

   IdUSER INT,
   FOREIGN KEY(IdUSER) REFERENCES COMPTE_UTILISATEUR(IdUSER) , 

   NbPIECEsRESERV INT,
   DATeRECUP DATE,
   DATeRETOUR DATE, 
   NbPIECEsPERDUES INT ,
   
   MARQUE VARCHAR(20) , 
   MODELE VARCHAR(20) , 
   ANNEeACHAT DATE ,
   FOREIGN KEY(MARQUE , MODELE , ANNEeACHAT) REFERENCES Lot(MARQUE , MODELE , ANNEeACHAT) 
);

















