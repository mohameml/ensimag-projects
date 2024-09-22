package model;

import java.util.HashMap;
import java.util.Random;
import java.util.Map;

public class Marche {
    
    static HashMap<Action , Integer> listAction ;


    public Marche() {
        listAction = new HashMap<>();
        this.setActionForSimulation();
    }



    public void setActionForSimulation() {
        Random rand = new Random();

        for(int i = 0 ; i < 40 ; i++ ) {
            listAction.put(new Action("Action"+i, rand.nextInt(301) + 100 , rand.nextGaussian(), rand.nextGaussian()), 100);
        }
    }


    public void modifieNbAction(int nb , Action choix) {

        // nb > 0 si on veux ajouter et < 0 si on veut dimunier
        listAction.put(choix, listAction.get(choix) + nb);

    }

    public int get(Action action) {

        return listAction.get(action);

    }

    public static Action getAction(String nom) {

        for (Map.Entry<Action , Integer> entry : listAction.entrySet()) {

            String name = entry.getKey().getNom();

            if(name.equals(nom)) {
                return entry.getKey();
            }
            
        }

        return null;
        
        
    }

    // proposer le portfeuille de variance minimale 

    // public static  HashMap<Action , Integer> getPrtfVarMin()
    // {
        
    //     // pas d'actif sans risque 


    //     // existe un actif sans risque 

    // }


}
