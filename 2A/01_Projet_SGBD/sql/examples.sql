
/* INSERTION DANS REFUGE */

INSERT INTO REFUGE
VALUES (
    'auberge-de-la-baie@bces.fr',
    'Auberge de la Baie',
    0154788421,
    'PMS',
    TO_DATE('2023-11-30 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_DATE('2023-12-04 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    '100',
    '50',
    'Bienvenue',
    'carte-bleue',
    50,
    75
);

SELECT *
FROM REFUGE;

/*INSERTION DANS REPAS*/

INSERT INTO REPAS 
VALUES('déjeuner');

INSERT INTO REPAS 
VALUES('souper');

INSERT INTO REPAS 
VALUES('dîner');

INSERT INTO REPAS
VALUES('casse-croûte');

SELECT *
FROM REPAS;

/*INSERTION DANS PROPOSE_REPAS*/

INSERT INTO PROPOSE_REPAS
VALUES('auberge-de-la-baie@bces.fr', 'déjeuner', 25.00);

SELECT *
FROM PROPOSE_REPAS;

/* INSERTION DANS COMPTE_UTILISATEUR*/

INSERT INTO COMPTE_UTILISATEUR 
VALUES(775566, 0.00, 0.00, 0.00);

SELECT *
FROM COMPTE_UTILISATEUR;

/*INSERTION DANS RESERVATION_REFUGE*/

INSERT INTO RESERVATION_REFUGE
VALUES(
       0000,
       TO_DATE('2023-11-30', 'YYYY-MM-DD'),
       12,
       'Auberge de la Baie',
       1245,
       3,
       2,
       100.00,
       775566,
       'auberge-de-la-baie@bces.fr'
       );

SELECT *
FROM RESERVATION_REFUGE;


/*INSERTION LOCATION_MATERIEL*/

INSERT INTO LOCATION_MATERIEL
VALUES(
        1234,
        665577,
        2,
        TO_DATE('2023-11-30', 'YYYY-MM-DD'),
        TO_DATE('2023-11-25', 'YYYY-MM-DD'),
        0,
        775566
        );

SELECT *
FROM LOCATION_MATERIEL;

/*INSERTION MEMBRE*/

INSERT INTO MEMBRE 
VALUES(
        'lupin@yahoo.fr',
        'azerty',
        'lupin',
        'Arsène',
        '38000',
        775566
        );
        
SELECT *
FROM MEMBRE;

/*INSERTION ADHERENT*/

INSERT INTO ADHERENT 
VALUES(
        665577,
        'lupin@yahoo.fr',
        'azerty',
        'lupin',
        'Arsène',
        '38000',
        775566
        );
        
SELECT *
FROM ADHERENT;

/* INSERTION DANS FORMATION*/

INSERT INTO FORMATION
VALUES(
       1,
       2023,
       'Ouistiti',
       TO_DATE('2023-11-30', 'YYYY-MM-DD'),
       12,
       5,
       'HOURA!',
       20.00,
       2
       );

SELECT *
FROM FORMATION;


/*INSERTION RESERVATION_FORMATION*/

INSERT INTO RESERVATION_FORMATION
VALUES(
        7,
        665577,
        'Ouistiti',
        0,
        775566,
        1,
        2023
        );

SELECT *
FROM RESERVATION_FORMATION;

/*INSERTION ACTIVITE*/

INSERT INTO ACTIVITE
VALUES('randonnée');

INSERT INTO ACTIVITE
VALUES('escalade');

SELECT *
FROM ACTIVITE;

/*INSERTION PROPOSE_FORM_ACTIVITE*/

INSERT INTO PROPOSE_ACTIVITE_FORM
VALUES(
        1,
        2023,
        'escalade'
        );
        
SELECT *
FROM PROPOSE_ACTIVITE_FORM;

/* INSERTION ABRE */

INSERT INTO ABRE
VALUES('mousqueton');

SELECT *
FROM ABRE;

/*INSERTION LOT*/

INSERT INTO LOT
VALUES(
        'lambert',
        'V2',
        2022,
        7,
        12.00,
        'je sais pas..',
        2025,
        'mousqueton'
        );

SELECT *
FROM LOT;

/*INSERTION COMPATIBLE_AVEC_ACTIVITE*/

INSERT INTO COMPATIBLE_AVEC_ACTIVITE
VALUES(
        'lambert',
        'V2',
        2022,
        'escalade'
        );
        
SELECT *
FROM COMPATIBLE_AVEC_ACTIVITE;

/*INSERTION EST_PARENT_DE*/

INSERT INTO EST_PARENT_DE
VALUES(
        'mousqueton',
        NULL
        );

SELECT *
FROM EST_PARENT_DE;


