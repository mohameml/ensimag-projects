package controle;

import vue.*; // Partie Vue du MVC 
import model.*; // Partie Modele du MVC 



public class Controle {
    
    static Marche marche ;
    static User user ;

    public Controle(User user ) {

        marche = new Marche();

        /*-------------------- des utilisateures pour la simulation ------------------- */
        Controle.user  = user;
        // User user2 = new User("Kahoula", 10000);




        /*------------------- la partie Vue ------------------ */

        new dashbordUser(user);

    }
    

    public  static Boolean  validerAchat(int nb , Action choix) {
        Boolean bool = true ;

        // // verfication du disponibilité :
        if(marche.get(choix) >= nb && user.getSolde() >= nb*choix.getPrix()) {

            // mise à jour du User : 
            user.ajouterAction(choix , nb);
            user.ModifieSolde(-nb*choix.getPrix());
            
            // mise à jour du Marche :
            marche.modifieNbAction(-nb, choix);
        }
        else  {
            bool = false;
        }


        return bool;

    }


    // verifier et valider un vend

    public  static Boolean  validerVent(int nb , Action choix) {
        Boolean bool = true ;

        
        // verifier s'il a le nb de l'action
        if(user.getNbAction(choix) < nb){
            bool = false ;
        }
        else{

            // mettre à jour le nombre d'ction dans le portfeuille du client 
            user.enleverAction(choix, nb);

            // ajouter le prix dans le solde du client
            user.ModifieSolde(nb*choix.getPrix());

            //mettre à jour le nombre de l'action dans le marché
            marche.modifieNbAction(nb, choix);

        }
    


        return bool;

    }






}
