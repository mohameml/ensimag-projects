
---- Réponse : Parcour des Refuges :
DROP VIEW Reserve ;

CREATE VIEW  Reserve AS 
SELECT Refuge.EMAIlREF , SUM(RESERVATION_REFUGE.NBrNUITS) AS nbDormirReserv ,  SUM(RESERVATION_REFUGE.NBrREPAsRESERVE) AS nbRepasReserv
FROM Refuge  , RESERVATION_REFUGE 
WHERE Refuge.EMAIlREF = RESERVATION_REFUGE.EMAIlREF 
GROUP BY Refuge.EMAIlREF;

SELECT  nom , dateOuv , DATEFERME , refuge.NBrREPAS - Reserve.nbRepasReserv AS nbRepasDispo , refuge.NBNUIT - Reserve.nbDormirReserv AS nbDormirDispo 
FROM Refuge , Reserve
WHERE Refuge.EMAIlREF = Reserve.EMAIlREF ; 