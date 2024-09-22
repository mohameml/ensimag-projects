DROP VIEW MatReserve ;
CREATE VIEW MatReserve AS 
SELECT marque , modele , anneeachat , SUM(NbPIECEsRESERV) AS nbReserve FROM LOCATION_MATERIEL 
GROUP BY marque , modele , anneeachat ;





DROP VIEW MatDispo;
CREATE VIEW MatDispo AS 
SELECT Lot.marque , Lot.modele , Lot.anneeachat , lot.NBPIECES , souscategorie , Lot.NBPIECES -  nbReserve as nbDispo
FROM Lot,MatReserve 
WHERE Lot.marque = MatReserve.marque 
AND Lot.modele = MatReserve.modele 
AND Lot.anneeachat = MatReserve.anneeachat 
;


