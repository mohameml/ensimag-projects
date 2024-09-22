package packages;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.time.LocalTime;




public class requettes 
{
    public requettes()
    {
        
    }



/*
 * 
 *
 * --------------------------------------------- Connexion && Inscription : ----------------------------------------- 
 * 
 * 
*/
    
    /*
     * ------------------- Connexion : ----------------- 
    */
    public static int connexionMembre(Connection connection , Scanner sc )
    {
        int idUser = -1;




        try 
        {
        
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) as count , IdUSER  FROM membre WHERE EMAIlMEMBRE=? AND MDP=? GROUP BY IdUSER");

            System.out.print("Entrez votre email svp : ");
            String emailAvecRetour = sc.nextLine();
            String email = emailAvecRetour.replace("\n", "");

            System.out.print("Entrez votre mot de passe svp : ");
            String passwordAvecRetour = sc.nextLine();
            String password = passwordAvecRetour.replace("\n", "");


            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet res = stmt.executeQuery();
            int verif = -1;


            while (res.next()) 
            {
                verif = res.getInt("COUNT");  
                idUser =  res.getInt("IdUSER");
                
            }

            if(verif==1)
            {
                System.out.println("Connexion réussie ");


            }
            else
            {
                System.out.println("Invalid Email ou mot de passe .");
                System.exit(0);

            }





        } 
        catch (SQLException e) 
        {

            e.printStackTrace();

        }
        
        


        return idUser ;
    }


    /*
     * 
     * ------------  Inscription : ------------ 
    */

    public static int inscriptionMembre(Connection connection , Scanner sc )
    {
        int idUser = -1 ;

        System.out.println("vous voulez inscrit en tant que  membre ou adhérent:\n\t tapez 1 --> membre , \n\t tapez 2 --> adhérent");
        
        int choix = sc.nextInt();
        sc.nextLine();





        if(choix==1)
        {
            // si le choixde l'utilisateur est 1 :
            try 
            {

                PreparedStatement stmt = connection.prepareStatement("INSERT INTO membre VALUES (? , ? , ? ,? , ? , ?) ");



                System.out.print("Entrez votre emial svp : ");
                String emial = sc.nextLine().replace("\n", "");

                System.out.print("Entrez votre mot de passe svp : ");
                String password = sc.nextLine().replace("\n", "");

                System.out.print("votre nom svp : ");
                String nom = sc.nextLine().replace("\n", "");

                System.out.print("Votre prenom svp: ");
                String prenom = sc.nextLine().replace("\n", "");

                System.out.print("Votre ADrPOST svp: ");
                String ADrPOST= sc.nextLine().replace("\n", "");


                // calculer l'idUser :

                idUser = idUser_(connection);

                Statement stmt2 = connection.createStatement();

                int  row1 = stmt2.executeUpdate("INSERT INTO COMPTE_UTILISATEUR(IdUSER) VALUES ("+ idUser+")");

                if(row1!=1)
                {

                    System.out.println("Inscription échouée");
                }
                // qyery :
                
                stmt.setString(1, emial);
                stmt.setString(2, password);
                stmt.setString(3, nom);
                stmt.setString(4, prenom);
                stmt.setString(5, ADrPOST);
                stmt.setInt(6,idUser);


                

                int row = stmt.executeUpdate();

                if(row==1)
                {
                    System.out.println("Inscription réussie");
                    connection.commit();
                }
                else
                {
                    System.out.println("Inscription échouée");
                    connection.rollback();
                }


            } 
            catch(SQLException e) 
            {
                    System.out.println("Inscription échouée");
                    e.printStackTrace();
                    // connection.rollback();            
            }

        }
        else if(choix==2)
        {

            try 
            {

                PreparedStatement stmt = connection.prepareStatement("INSERT INTO membre VALUES (? , ? , ? ,? , ? , ?) ");



                System.out.print("Entrez votre emial svp : ");
                String emial = sc.nextLine().replace("\n", "");

                System.out.print("Entrez votre mot de passe svp : ");
                String password = sc.nextLine().replace("\n", "");

                System.out.print("votre nom svp : ");
                String nom = sc.nextLine().replace("\n", "");

                System.out.print("Votre prenom svp: ");
                String prenom = sc.nextLine().replace("\n", "");

                System.out.print("Votre ADrPOST svp: ");
                String ADrPOST= sc.nextLine().replace("\n", "");


                // calculer l'idUser :

                idUser = idUser_(connection);

                Statement stmt2 = connection.createStatement();

                int  row1 = stmt2.executeUpdate("INSERT INTO COMPTE_UTILISATEUR(IdUSER) VALUES ("+ idUser+")");

                if(row1!=1)
                {

                    System.out.println("Inscription échouée");
                }
                // qyery :
                
                stmt.setString(1, emial);
                stmt.setString(2, password);
                stmt.setString(3, nom);
                stmt.setString(4, prenom);
                stmt.setString(5, ADrPOST);
                stmt.setInt(6,idUser);


                

                int row = stmt.executeUpdate();

                if(row==1)
                {
                    System.out.println("Inscription réussie");
                    connection.commit();
                }
                else
                {
                    System.out.println("Inscription échouée");
                    connection.rollback();
                }
                // l'ajout du membre dans la table des adhérant :
                PreparedStatement stmt3 = connection.prepareStatement("INSERT INTO ADHERENT VALUES (? , ? , ? , ? ,? , ? , ?) ");
                // qyery :
                
                int idadh = idAdh_(connection);

                stmt3.setInt(1 , idadh);
                stmt3.setString(2, emial);
                stmt3.setString(3, password);
                stmt3.setString(4, nom);
                stmt3.setString(5, prenom);
                stmt3.setString(6, ADrPOST);
                stmt3.setInt(7,idUser);


                int row3 = stmt3.executeUpdate();


                if(row3!=1)
                {
                    System.out.println("échouée lors de l'ajout dans la table ADHERENT");
                    connection.rollback();
                }

                connection.commit();


            } 
            catch(SQLException e) 
            {
                    System.out.println("Inscription échouée");
                    e.printStackTrace();
                    // connection.rollback();            
            }


        }
        else
        {
            System.err.println("choix invalide ");
            System.exit(0);
        }





    

        return idUser;



    }


/*
 * 
 *--------------------------------- Parcour des informations : --------------------------------------------- 
 * 
 * 
*/


    
    /*
     * ------------------ Parcour des formations : -------------------- 
    */

    public static void afficherFormation(Connection connection)
    {

        try 
        {
            ResultSet res = executeFileSQL(connection , "sql/parcourInfo/afficheformation.sql");


            if(res==null)
            {
                System.err.println("Erreur SQL");
                System.exit(1);
            }

            // affichage de résultat : 
            System.out.println("Anneé  \t \tnuméro    nom  \t activités   dateDém  \t Duree  nbPlaceRes ");

            while(res.next())
            {
               Date  annee = res.getDate("anneeFORM");
               int numero = res.getInt("IDFORM");
               String nom = res.getString("nom");
               String activté = res.getString("nomActivite");
               Date datedem = res.getDate("dateDem");
               int duree = res.getInt("duree");
               int nbRestant = res.getInt("nbrRestant");
               System.out.println(annee + "\t  " + numero + "\t   " + nom + "\t  " + activté + "\t   " + datedem + "\t   " + duree + "\t   " + nbRestant );



            }



            


        } 
        catch(SQLException e1) 
        {
            e1.printStackTrace();
        }



    }

    /*
     * ---------------------- Parcour des  Refuges : ------------------------------ 
    */

    public static void afficherRefuge(Connection connection )
    {
        try{


            ResultSet res = executeFileSQL(connection, "sql/parcourInfo/afficheRefuge.sql");

            if(res==null)
            {
                System.err.println("Erruer SQL");
                System.exit(1);
            }

            System.out.println("nom , dateOuv , dateFreme , nbRepasDispo , nbDormirDispo \n");

            while(res.next())
            {
                String nom = res.getString("nom");
                Date dateOuv = res.getDate("dateOuv");
                Date dateFreme = res.getDate("dateFerme");
                int nbDispoRepas = res.getInt("nbRepasDispo");
                int nbNuitDispo = res.getInt("nbDormirDispo");

                System.out.println(nom + ", " + dateOuv + " ," + dateFreme + " , " + nbDispoRepas + " , \t\t" + nbNuitDispo );
            }

            
            

            
        }
        catch (SQLException e1) 
        {

            e1.printStackTrace();
        }


    }


    /*
     *----------------------------------- Parcour des matériels : -----------------------------------------
    */

    public static void affichageMatériels(Connection connection , Scanner sc)
    {

        // affichage par nombre de piéces réserves ou par activités :
        System.out.println("affichag par catégorie -----> tapez 1");
        System.out.println("affichag par activité  -----> tapez 2");


        System.out.print("Tapez votre choix svp : ");

        int choix = sc.nextInt();

        if(choix==1)
        {


            // l'affichae par Arbre de catégories :
            try 
            {
                Statement stmt = connection.createStatement();


                ResultSet res = stmt.executeQuery("SELECT * FROM estparentDe WHERE sousCategorie2='EPI'");                
                

                int i = 1 ;
                

                HashMap<Integer , String> dict = new HashMap<>();



                while (res.next()) 
                {
                    String souscategorie = res.getString("souscategorie1");
                    
                    System.out.println(souscategorie +" ----->  tapez " + i );

                    
                    dict.put(i , souscategorie);
                    i++ ;                    

                    


                }

                System.out.println("Si vous confirmez le choix tapez : -1");


                boolean bool = true ; 

                
                int choix2 = 0  ; 
                int choixPrec = 0 ;

                while(bool)
                {

                    choixPrec = choix2 ; 
                    System.out.print("Tapez un nouveau choix ou confirmez (avce -1) : ");

                    choix2 = sc.nextInt();
                    sc.nextLine();


                    if(choix2==-1)
                    {
                        bool = false ;
                    }
                    else
                    {

                        if(choix2 > i )
                        {
                            System.out.println("Choix invalide");
                            System.exit(0);
                        }
                        
                        

                        ResultSet res2 = stmt.executeQuery("SELECT * FROM estparentDe WHERE sousCategorie2='"+dict.get(choix2) +"'");

                        if(res2==null)
                        {
                            System.out.println("Il n'y apas de sous catégorie de cette catégorie ");
                        }


                        while(res2.next())
                        {
                            String souscategorie = res2.getString("souscategorie1");
                            
                            if(!dict.containsValue(souscategorie))
                            {
                                System.out.println(souscategorie +" ----->  tapez " + i );
                                dict.put(i , souscategorie);

                                i++;
                            }

                        }                        
                    }

                }


                ResultSet res3 = stmt.executeQuery("SELECT Lot.marque , Lot.modele , Lot.anneeachat , lot.NBPIECES , souscategorie , Lot.NBPIECES  as nbDispo "+"FROM Lot WHERE souscategorie='" + dict.get(choixPrec) +"' AND ( (marque , modele , anneeAchat) NOT IN (SELECT marque , modele , anneeAchat FROM LOCATION_MATERIEL ))"
                                );
                

                // System.out.println("------------- voici les matériels de sous catégortie de votre choix qui ne sont pas encore réserver :-----------------");

                System.out.println("marque\t,modele\t,nbpiecesTotale\t,nbpiecesDispo ");
                while(res3.next())
                {
                    String marque = res3.getString("Marque");
                    String modele  = res3.getString("Modele");
                    int nbPiecesTotal  = res3.getInt("NBPIECES");
                    int nbPieces  = res3.getInt("nbDispo");




                    System.out.println(marque + "\t," + modele + "\t ," + nbPiecesTotal + "\t,\t" + nbPieces);
                    
                }


                executeFileSQL(connection , "sql/parcourInfo/afficheMatériels.sql");


                ResultSet res4 = stmt.executeQuery("SELECT * FROM MatDispo WHERE souscategorie='"+dict.get(choixPrec) +"'");
                while(res4.next())
                {
                    String marque = res4.getString("Marque");
                    String modele  = res4.getString("Modele");
                    int nbPiecesTotal  = res4.getInt("NBPIECES");
                    int nbPieces  = res4.getInt("nbDispo");     




                    System.out.println(marque + "\t," + modele + "\t," + nbPiecesTotal  + "\t,\t"+ nbPieces);
                    
                }





            
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }




        }
        else if(choix==2)
        {
            // l'affichage par activités : 


            try
            {
                Statement stmt = connection.createStatement();


                ResultSet res1 = stmt.executeQuery("SELECT * FROM ACtivite");

                int i = 1  ;

                System.out.println("choix de l'activite :");
                HashMap<Integer , String> dict = new HashMap<Integer , String>();


                while(res1.next())
                {
                    String nomAc = res1.getString("NOMACTIVITE");

                    System.out.println("\n\t"+nomAc+"------>"+i);
                    dict.put(i,nomAc);
                    i++;
                }


                int choixAc = sc.nextInt();
                sc.nextLine();

                executeFileSQL(connection , "sql/parcourInfo/afficheMatériels.sql");
                

                System.out.println("marque\t,modele\t,nbpiecesTotale\t,nbpiecesDispo ");


                ResultSet res2 = stmt.executeQuery
                (
                "SELECT Matdispo.marque , MAtDispo.modele , MatDispo.anneeachat, NBPIECES  , nbdispo from MatDispo , COMPATIBLE_AVEC_ACTIVITE "+
                "WHERE  MatDispo.marque = COMPATIBLE_AVEC_ACTIVITE.marque "+
                "AND MatDispo.modele = COMPATIBLE_AVEC_ACTIVITE.modele "+ 
                "AND MAtDispo.anneeachat = COMPATIBLE_AVEC_ACTIVITE.anneeachat "+
                "AND  nomactivite='"+dict.get(choixAc)+"'"
                );

                while(res2.next())
                {
                    String marque = res2.getString("Marque");
                    String modele  = res2.getString("Modele");
                    int nbPiecesTotal  = res2.getInt("NBPIECES");
                    int nbPieces  = res2.getInt("nbDispo");     




                    System.out.println(marque + "\t," + modele + "\t," + nbPiecesTotal  + "\t,\t"+ nbPieces);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }

            
        }
        else 
        {
            System.err.println("choix Invalide ");
            System.exit(0);
        }


    }




/*
 *
 * --------------------------------------------------- Réservation : ------------------------------------- 
 * 
*/


    /*
     *----------------- Réservation d'une formation: ---------------------------  
    */
    public static void réserverFormation(Connection connection , Scanner sc , int id )
    {

        // verifiction que : USER est un adhérant :
        
        try
        {


            Statement stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT idUSER FROM ADHERENT ");
        
            
            int s = 0 ;

            while (res.next()) 
            {
                int idadh = res.getInt("idUSER"); 
                
                
                if(idadh==id)
                {
                    s+=1;
                    break;
                    
                }
            }


            if(s!=1) 
            {
                System.err.println("Désolé vous n'êtes pas adhérant ");
                  System.exit(0);  
            }

            

        
        
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        








        System.out.print("Donnez l'année de la foramtion (par example 2022 ..) :");
        int anneeFor = sc.nextInt();
        sc.nextLine();

        System.out.print("Donnez le numéro de formation :");
        int numeroFor  = sc.nextInt();
        sc.nextLine();



        try{

            executeFileSQL(connection , "sql/réservation/réserveFormation.sql");

            String date = "TO_DATE('"+anneeFor+"-01-01','YYYY-MM-DD')";

            Statement stmt = connection.createStatement();

            
    



            ResultSet res = stmt.executeQuery("SELECT nbDispo FROM NBDISPOFOR WHERE IdFORM="+numeroFor +"AND ANNEeFORM=" + date);


            

            int nbReservFor = -1 ;

            while(res.next())
            {
                nbReservFor = res.getInt("nbDispo");

            }

            if(nbReservFor >0 )
            {
                // donc on peut réserver une formation :

                ResultSet res3 = stmt.executeQuery("SELECT COUNT(*) as count FROM RESERVATION_FORMATION");

                int idReservFor = -1  ;


                while (res3.next()) 
                {
                    idReservFor = res3.getInt("count");    
                }

                ResultSet res4 = stmt.executeQuery("SELECT MAX(RANG) as max FROM RESERVATION_FORMATION WHERE NUMERO="+numeroFor +"AND ANNEE=" + date);

                int rang = -1 ;
                
                
                

                while (res4.next()) 
                {
                    rang = res4.getInt("max");    
                }

                idReservFor++;
                rang++; 

                String query = String.format("INSERT INTO RESERVATION_FORMATION VALUES (%d ,%d ,%d , %d ,%s)",idReservFor,rang ,id,numeroFor, date);

                int row = stmt.executeUpdate(query);
                
                if(row==1)
                {
                    System.out.println("Réservation bien passée ");
                    connection.commit();
                }
                else
                {
                    connection.rollback();
                }

                


            }
            else
            {
                // 

                System.out.println("Désole il n'existe plus de place, on va vous mettre en liste d'attente  : -----------> votre rang est " + (-1*nbReservFor)+1);
            }


        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }





    }


    /*
     *----------------- Réservation de Refuge : ---------------------------  
    */
    public static void réserverRefuge(Connection connection , Scanner sc, int idUser)
    {
        try 
        {
            PreparedStatement stmt;
            ResultSet res;

            String emailRefuge;
            String nomRefuge;
            	
            /* On exécute ls script de création des tables de réservations et de disponiblités */
            /*-------------------------------------------------------------------------*/
            res = executeFileSQL(connection , "sql/réservation/réserveRefuge.sql");
            if(res==null)
            {
                System.err.println("Erreur SQL : Echec lors de la création des tables de réservations et de disponiblités");
                System.exit(1);                
            }
            /*-------------------------------------------------------------------------*/
                
                
            /* On affiche tous les refuges (nom + email) */
		    /*-------------------------------------------------------------------------*/
            stmt =  connection.prepareStatement("SELECT emailref, nom FROM Refuge ");
            res = stmt.executeQuery();
            if(res==null)
            {
                System.err.println("Erreur SQL : Echec lors de la sélection des noms et emails des refuges");
                System.exit(1);                
            }
            System.out.println("Liste des refuges :");
                
            while(res.next())
            {
                String email = res.getString("emailref");
                String nom = res.getString("nom");
		        System.out.println(nom + " : " + email);
            }
            /*-------------------------------------------------------------------------*/
            	
            	
            /* On demande quel refuge est sollicité (email) et on cherche le nombre de nuités et de repas disponibles (POUR PERIODE DE RESERVATION) et on affiche pour informer l'utilisateur */
            /*-------------------------------------------------------------------------*/
            System.out.print("\nEntrez l'adresse e-mail du refuge dans lequel vous souhaitez réserver :");
                
            emailRefuge = sc.nextLine().replace("\n", "");
            stmt = connection.prepareStatement("SELECT NBNUIT FROM Refuge WHERE emailref=?");
            stmt.setString(1, emailRefuge);

            /*------ correction d'une bug : on ne peut pas appeler getInt en dehros du while(res.next) */
            ResultSet  resDispo = stmt.executeQuery();
            
            int nbDormirDispo = 0 ; 

            while(resDispo.next())
            {
                nbDormirDispo = resDispo.getInt("NBNUIT");

            }

            stmt = connection.prepareStatement("SELECT NBREPASDISPO FROM Dispo WHERE Dispo.emailref=? ");
            stmt.setString(1, emailRefuge);

            int nbRepasDispo = 0 ;
            res = stmt.executeQuery();

            while(res.next())
            {
                nbRepasDispo = res.getInt("NBREPASDISPO");
            }

            stmt = connection.prepareStatement("SELECT nom FROM Refuge WHERE Refuge.emailref=? ");
            stmt.setString(1, emailRefuge);


            nomRefuge = "" ;
            res = stmt.executeQuery();

            while(res.next())
            {
                nomRefuge = res.getString("nom");
            }

            System.out.println("Le refuge" + nomRefuge + "d'email " + emailRefuge + " a " + nbDormirDispo + " nuités de disponible ainsi que " + nbRepasDispo + " repas de disponible.");
            /*-------------------------------------------------------------------------*/
                
                
            /* On traite les nuités */
            /*-------------------------------------------------------------------------*/
            int nbNuits = 0;
            java.sql.Date dateReservation = new java.sql.Date(0);
            // Il faut boucler pour redonner une tentative à l'utilisateur
            boolean done = false;
            while(!done)
            {
                System.out.println("Attention, il n'est pas possible de ne réserver à la fois aucune nuité et à la fois aucun repas !");
                System.out.print("Entrez la date de réservation (format : YYYY-MM-DD) : ");
                String dateReservationString = sc.nextLine().replace("\n", "");
                        
                /* Traiter la date de réservation */
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
                long millis = sdf.parse(dateReservationString).getTime();
                dateReservation = new java.sql.Date(millis);


                System.out.print("Entrez le nombre de nuités que vous souhaitez réserver : ");
                nbNuits = sc.nextInt();
                sc.nextLine(); /*-- RQ : il faut utilisez nextLine() pour consomez le \n (Entrez tapez par l'utilisateur) */

                
                /* A itérer sur toutes les dates de la période de réservation */
                boolean previousOk = true;
                for(int i = 0; previousOk && i < nbNuits; i++)
                {
                    stmt = connection.prepareStatement("SELECT COUNT(*) AS count FROM Reservation_refuge WHERE Reservation_refuge.emailref=? AND Reservation_refuge.date_reservation <= ? AND Reservation_refuge.date_reservation + ? <= ?");

                    stmt.setString(1, emailRefuge);
                    stmt.setDate(2, dateReservation);
                    stmt.setInt(3, i);
                    stmt.setDate(4, dateReservation);

                    res = stmt.executeQuery() ;
                    int nb = 0 ;
                    while(res.next())
                    {
                        nb = res.getInt("count");
                    }

                    previousOk = nbDormirDispo > nb;
                }

                if(previousOk)
                {
                    // Il y a au moins une place toutes les nuits de la période
                    done = true;
                }
                else
                {
                    // Il y a au moins une nuit sans aucune place de disponible
                    System.out.println("Désolé, ce refuge a aucune place lors d'au moins une nuit.");
                }
            }
            /*-------------------------------------------------------------------------*/


            /* On traite les repas */
            /*-------------------------------------------------------------------------*/
            int dejeuner = 0;
            int diner = 0;
            int souper = 0;
            int casseCroute = 0;
            int nbRepas = 0;
            done = false;

            while(!done)
            {

                if(nbNuits == 0)
                    System.out.println("Attention, vous n'avez pas réservé de nuités, il vous faut donc réserver au moins un repas !");

                System.out.print("Voulez vous réserver le déjeuner (1 : oui | 0 : non) : ");
                dejeuner = sc.nextInt();
                sc.nextLine();

                System.out.print("Voulez vous réserver le dîner (1 : oui | 0 : non) : ");
                diner = sc.nextInt();
                sc.nextLine();


                System.out.print("Voulez vous réserver le souper (1 : oui | 0 : non) : ");
                souper = sc.nextInt();
                sc.nextLine();

                System.out.print("Voulez vous réserver le casse-croûte (1 : oui | 0 : non) : ");
                casseCroute = sc.nextInt();
                sc.nextLine();

                nbRepas = dejeuner + diner + souper + casseCroute;

                if(nbRepas > 0)
                {
                    /* A itérer sur toutes les dates de la période de réservation */
                    boolean previousOk = true;
                    for(int i = 0; previousOk && i < nbNuits; i++)
                    {
                        stmt = connection.prepareStatement("SELECT SUM(NBrREPAsRESERVE) AS sum FROM Reservation_refuge WHERE Reservation_refuge.emailref=? AND Reservation_refuge.date_reservation <= ? AND Reservation_refuge.date_reservation + ? <= ?");
                        stmt.setString(1, emailRefuge);
                        stmt.setDate(2, dateReservation);
                        stmt.setInt(3, i);
                        stmt.setDate(4, dateReservation);
                        
                        int nb = 0;
                       	res = stmt.executeQuery() ;
                        while(res.next())
                        {
                        nb = res.getInt("sum");
                    	}

                        previousOk = nbRepasDispo - nbRepas > nb;
                    }

                    if(previousOk)
                    {
                        // Il y a au moins une place toutes les nuits de la période
                        done = true;
                    }
                    else
                    {
                        // Il y a au moins une nuit sans aucune place de disponible
                        System.out.println("Désolé, ce refuge n'a pas assez de repas lors d'au moins un jour.");
                    }
                }
            }
            /*-------------------------------------------------------------------------*/


            /* On procède à la réservation */
            /*-------------------------------------------------------------------------*/
            stmt = connection.prepareStatement("SELECT prixnuite FROM Refuge WHERE Refuge.emailref=? ");
            stmt.setString(1, emailRefuge);
            int prixNuite = 0;
            res = stmt.executeQuery() ;
            while(res.next())
            {
            	prixNuite = res.getInt("prixnuite");
            }
            
            stmt = connection.prepareStatement("SELECT MAX(IdReserv) as max FROM Reservation_refuge");
            res = stmt.executeQuery();
            int idReservationRefuge = 0;
            
            while(res.next())
            {
            	idReservationRefuge = res.getInt("max") + 1;
            }

            int heureReservation = LocalTime.now().getHour();

            stmt = connection.prepareStatement("INSERT INTO RESERVATION_REFUGE VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, idReservationRefuge);
            stmt.setDate(2, dateReservation);
            stmt.setInt(3, heureReservation);
            stmt.setInt(4, nbNuits);
            stmt.setInt(5, nbRepas);
            stmt.setInt(6, nbNuits * prixNuite);
            stmt.setString(7, emailRefuge);
            stmt.setInt(8, idUser);
            
            System.out.println("Votre réservation a été prise en compte, merci !");
		    /*-------------------------------------------------------------------------*/
                        
        } 
        catch(SQLException | ParseException e) 
        {
            e.printStackTrace();
        }
    }


    /*
     *----------------- Réservation de matériels: ---------------------------  
    */
    public static void réserverMatériels(Connection connection , Scanner sc , int id )
    {
        

        // verifiction que : USER est un adhérant :
        
        try
        {


            Statement stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT idUSER FROM ADHERENT ");
        
            
            int s = 0 ;

            while (res.next()) 
            {
                int idadh = res.getInt("idUSER"); 
                
                
                if(idadh==id)
                {
                    s+=1;
                    break;
                    
                }
            }


            if(s!=1) 
            {
                System.err.println("Désolé vous n'êtes pas adhérant ");
                  System.exit(0);  
            }

            

        
        
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


        // réservation matériels : 
        System.out.print("Le nombre de pièces à réserver :");

        int nb = sc.nextInt() ; 
        sc.nextLine(); // pour le \n 

        System.out.print("La date de réservation sous la forme YYYY-MM-DD : ");
        String dateRéserv = sc.nextLine();

        System.out.print("La date de récupération sous la forme YYYY-MM-DD : ");
        String dateRécup = sc.nextLine();

        System.out.println("le Lot que voulez vous réservez :");
        
        System.out.print("\tMarque :");
        String marque = sc.nextLine();

        System.out.print("\tModele :");
        String modele = sc.nextLine();

        System.out.print("\tAnnee d'achat(2022 ..) :");
        String anneeAchat = sc.nextLine();

 
        String annee = "TO_DATE('"+anneeAchat+"01-01' , 'YYYY-MM-DD')";


        try {
            
            Statement stmt = connection.createStatement(); 


            /*-------------- verification de disponibilités : --------------  */
            
            executeFileSQL(connection , "sql/parcourInfo/afficheMatériels.sql");



            

            Statement stmt2 = connection.createStatement();

            String query2 = String.format("SELECT  nbDispo FROM MatDispo  WHERE marque ='%s' AND modele ='%s' AND anneeachat =%s" , marque , modele , annee);





            ResultSet res0 = stmt2.executeQuery(query2);

            int nbdispo = 0 ;

            while(res0.next())
            {
                nbdispo = res0.getInt("nbDispo");
            }




            if(nbdispo < nb)
            {
                System.out.println("Désolé, le nombre disponible est inférieur au nombre que vous voulez");

            }
            else
            {
                
                ResultSet res1 = stmt.executeQuery("SELECT COUNT(*) as count FROM LOCATION_MATERIEL");

                int idLoc = 1 ;
                while(res1.next())
                {
                    idLoc+=res1.getInt("count");
                }

                String dateR = "TO_DATE('"+dateRéserv+"', 'YYYY-MM-DD')";
                String dateRe = "TO_DATE('"+dateRécup+"', 'YYYY-MM-DD')";

                String query = String.format("INSERT INTO LOCATION_MATERIEL VALUES (%d , %d ,%d ,%s ,%s , %d , '%s' ,'%s' , %s) " , 
                            idLoc , id , nb ,dateR , dateRe , 0 , marque , modele , annee);

                int row = stmt.executeUpdate(query);

                if(row!=1)
                {
                    System.out.println("réservation échouée");
                    connection.rollback();
                }
                else if(row==1)
                {
                System.out.println("réservation bien pasée");
                connection.commit();

            }
            }



        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }



    }



/*
 * ------------------------- Droit à l'oubli --------------------------
*/

public static void oublierMoi(Connection connection , int id )
{
    // est ce que il s'agit d'un adhérant ou non : 

        // verifiction que : USER est un adhérant :
        
        boolean estADH = false ;
        try
        {


            Statement stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT idUSER FROM ADHERENT ");
        
            
            int s = 0 ;

            while (res.next()) 
            {
                int idadh = res.getInt("idUSER"); 
                
                
                if(idadh==id)
                {
                    s+=1;
                    break;
                    
                }
            }


            if(s!=1) 
            {
                estADH = false ;
            }
            else
            {
                estADH = true ;
            }

            

        
        
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        } 



        try
        {
            Statement stmt = connection.createStatement();


            int row = stmt.executeUpdate("DELETE FROM MEMBRE WHERE idUSER="+id);

            if(row==1)
            {
                System.out.println("DROIT de l'Oublie : bien paseé ");
                connection.commit();
            }
            else
            {
                System.out.println("----------- Erreur ------------");
                connection.rollback();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }



        if(estADH)
        {
        try
        {
            Statement stmt = connection.createStatement();


            int row = stmt.executeUpdate("DELETE FROM ADHERENT WHERE idUSER="+id);

            if(row==1)
            {
                System.out.println("DROIT de l'Oublie : bien paseé ");
                connection.commit();
            }
            else
            {
                System.out.println("----------- Erreur ------------");
                connection.rollback();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


        }


}

    
/*
 * --------- fonction : exécuter un fichier .sql ---------------
 */

    public static ResultSet executeFileSQL(Connection connection , String nameFile)
    {


        try 
        {
            Statement stmt = connection.createStatement();

            // lecture de fichier SQL : 
            BufferedReader reader = new BufferedReader(new FileReader(nameFile));

            StringBuilder query = new StringBuilder();
            String line ; 

            ResultSet res = stmt.executeQuery("SELECT 0 FROM Membre"); // Init : ?? 

            while((line = reader.readLine())!=null)
            {
                String lineCopie = new String(line);

                if(!lineCopie.trim().isEmpty() && !lineCopie.trim().startsWith("--"))
                {
                    // si n'est pas une ligne vide , ni un commentaire:
                    query.append(line).append(" ");
                }


                if(lineCopie.trim().endsWith(";"))
                {
                    // il s'agit de la fin d'une Réquette : 
                    res = stmt.executeQuery(query.toString().replace(";" , " "));
                
                    query = new StringBuilder();
                }
            }
            
            
            reader.close();


            return res ;
        } 
        catch (SQLException e1) 
        {
            e1.printStackTrace();
        }
        catch(IOException e2)
        {
            e2.printStackTrace();
        }

        return null ; // en cas d'errure  
    }




    /*
     * ---------------- Calcule le idUser ------------------
     */
    public static int idUser_(Connection connection)
    {
        int iduser = 1 ;

        try
        {
            Statement stmt2 = connection.createStatement() ;

            ResultSet res0 = stmt2.executeQuery("SELECT COUNT(*) as count FROM COMPTE_UTILISATEUR");
            // ResultSet res1 = stmt2.executeQuery("SELECT COUNT(*) as count FROM ADHERENT");


            

            while(res0.next())
            {
                iduser += res0.getInt("count");
            }

            // while(res1.next())
            // {
            //     nbUser += res1.getInt("count");
            // }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }





        return iduser ;
    }



    public static int idAdh_(Connection connection)
    {
        int id = 1 ;

        try
        {
            Statement stmt2 = connection.createStatement() ;

            ResultSet res0 = stmt2.executeQuery("SELECT COUNT(*) as count FROM ADHERENT");
            // ResultSet res1 = stmt2.executeQuery("SELECT COUNT(*) as count FROM ADHERENT");


            

            while(res0.next())
            {
                id += res0.getInt("count");
            }

            // while(res1.next())
            // {
            //     nbUser += res1.getInt("count");
            // }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }





        return id ;
    }




}
