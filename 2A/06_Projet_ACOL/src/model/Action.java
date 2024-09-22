package model;



public class Action {
    
    private String nom ;
    private int prix ;
    private double rentabilite;
    private double risque ;

    public Action(String nom , int prix , double re , double risque  ) {
        this.nom = nom ;
        this.prix = prix ;
        this.rentabilite = re ;
        this.risque = risque;

    }

    public int getPrix() {
        return this.prix;
    }


    public String getNom() {
        return this.nom;
    }



    public double getRentabilite() {
        return this.rentabilite;
    }


    public double getRisque() {
        return this.risque;
    }



    @Override
    public boolean equals(Object o) {
        if(o instanceof Action) {
            Action action =(Action)o ;
            return action.getNom() == this.getNom();
        }

        return false;
    }
}


