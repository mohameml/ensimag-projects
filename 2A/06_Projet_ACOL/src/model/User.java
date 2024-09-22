package model;

// import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String nom ;
    private int solde ;
    private HashMap<Action , Integer> ListActions ;


    public User(String nom , int solde) {
        this.nom = nom ;
        this.solde = solde;
        this.ListActions = new HashMap<>();

    }
    // ajouter nb de l'action choix au portfeuille du client

    public void ajouterAction(Action choix , int nb) {
        
        if(this.ListActions.get(choix) == null) {
    
            ListActions.put(choix , nb );
    
        }
        else {
    
            ListActions.put(choix ,nb + this.ListActions.get(choix) );
        }

    }

    // enlever nb de l'action choix au portfeuille du client

    public void enleverAction(Action choix , int nb) {

        if(this.ListActions.get(choix) == nb)
        {
            this.ListActions.remove(choix);
        }
        else{
            ListActions.put(choix ,this.ListActions.get(choix) - nb);
        }
        

    }




    public String getNom() {
        return this.nom;
    }


    public int getSolde() {
        return this.solde;
    }


    public void ModifieSolde(int montant ) {
        this.solde += montant;
        

    }

    // return le nombre de l'action specifiée 

    public int getNbAction(Action choix)
    {
        if(this.ListActions.get(choix) == null)
        {
            return 0;
        }else{
            return ListActions.get(choix);
        }
    }
    

    // return les actions du client
    public Object[][] getActions() {
        Object[][] objet = new Object[100][4];
        int i = 0 ;
        for (Map.Entry<Action , Integer> entry : this.ListActions.entrySet()) {

            String name = entry.getKey().getNom();
            int nb = entry.getValue();
            objet[i][0] = name ;
            objet[i][1] = nb ;
            objet[i][2] = entry.getKey().getPrix() ;
            objet[i][3] = nb*entry.getKey().getPrix() ;
            i++ ;

        }

        return objet;
    
    
    }



    
    
}
