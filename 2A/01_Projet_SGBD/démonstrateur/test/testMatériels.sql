--

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption , sousCategorie) 
VALUES ('Marque1' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') , 20 , 'rien' , 120 , 2025  ,'AB');

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) 
VALUES ('Marque2' , 'Modéle1' , TO_DATE('2022-01-01', 'YYYY-MM-DD')  , 20 , 'rien' , 120 , 2025 , 'mousqueton symétrique');

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) 
VALUES ('Marque3' , 'Modéle2' , TO_DATE('2023-01-01', 'YYYY-MM-DD')  , 20 , 'rien' , 120 , 2025 , 'mousqueton HMS');

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) 
VALUES ('Marque4' , 'Modéle2' , TO_DATE('2024-01-01', 'YYYY-MM-DD')  , 20 , 'rien' , 120 , 2025 , 'mousqueton HMS');

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) 
VALUES ('Marque1' , 'Modéle3' , TO_DATE('2020-01-01', 'YYYY-MM-DD')  , 20 , 'rien' , 120 , 2025 , 'AB');

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) 
VALUES ('Marque5' , 'Modéle2' , TO_DATE('2029-01-01', 'YYYY-MM-DD')  , 20 , 'rien' , 120 , 2025 , 'mousqueton symétrique');

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) 
VALUES ('Marque6' , 'Modéle1' , TO_DATE('2023-01-01', 'YYYY-MM-DD')  , 20 , 'rien' , 150 , 2025 , 'mousqueton');


-- 

SELECT * FROM Arbre ;

INSERT INTO Arbre(categorie) VALUES ('EPI');  
INSERT INTO Arbre(categorie) VALUES ('mousqueton');  
INSERT INTO Arbre(categorie) VALUES ('AB');  
INSERT INTO Arbre(categorie) VALUES ('mousqueton symétrique');  
INSERT INTO Arbre(categorie) VALUES ('mousqueton HMS');  

-- 
INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('mousqueton HMS' , 'mousqueton') ;
INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('mousqueton symétrique' , 'mousqueton') ;
INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('mousqueton' , 'EPI') ;
INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('AB' , 'EPI') ;

SELECT * FROM EstParentDe ;


-- 


INSERT INTO COMPATIBLE_AVEC_ACTIVITE VALUES ('Marque1' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') , 'AC1' ) ; 
INSERT INTO COMPATIBLE_AVEC_ACTIVITE VALUES ('Marque1' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') , 'AC2' ) ; 
INSERT INTO COMPATIBLE_AVEC_ACTIVITE VALUES ('Marque1' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') , 'AC3' ) ; 

INSERT INTO COMPATIBLE_AVEC_ACTIVITE VALUES ('Marque2' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') , 'AC2' ) ; 
INSERT INTO COMPATIBLE_AVEC_ACTIVITE VALUES ('Marque2' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') , 'AC3' ) ; 

SELECT * FROM COMPATIBLE_AVEC_ACTIVITE ; 


-- 



INSERT INTO LOCATION_MATERIEL VALUES 
(1,5, 10 , TO_DATE('2023-05-01', 'YYYY-MM-DD') , TO_DATE('2023-05-15', 'YYYY-MM-DD') , 0 , 'Marque1' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') ) ;


INSERT INTO LOCATION_MATERIEL VALUES 
(2,5, 15 , TO_DATE('2023-10-01', 'YYYY-MM-DD') , TO_DATE('2023-11-15', 'YYYY-MM-DD') , 0 , 'Marque2' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') ) ;


INSERT INTO LOCATION_MATERIEL VALUES 
(3,6, 18 , TO_DATE('2023-04-15', 'YYYY-MM-DD') , TO_DATE('2023-04-25', 'YYYY-MM-DD') , 0 , 'Marque1' , 'Modéle1' ,  TO_DATE('2022-01-01', 'YYYY-MM-DD') ) ;



SELECT * FROM LOCATION_MATERIEL ; 


-- 


